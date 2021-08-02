package com.herscher.swiftlydemo.specials

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.herscher.swiftlydemo.databinding.FragmentSpecialsListBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SpecialsListFragment : Fragment() {
    companion object {
        fun newInstance() = SpecialsListFragment()
    }

    // Could inject via Dagger if I have time
    private val viewModel: SpecialsListViewModel by viewModels {
        ViewModelFactory(requireActivity().applicationContext)
    }

    private val disposable = CompositeDisposable()

    private var binding: FragmentSpecialsListBinding? = null

    private lateinit var productAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productAdapter = ProductListAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSpecialsListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            retry.setOnClickListener { loadSpecialsList() }

            recyclerView.apply {
                val flexManager = FlexboxLayoutManager(context)
                flexManager.justifyContent = JustifyContent.CENTER
                layoutManager = flexManager
                adapter = productAdapter
            }
        }

        disposable.add(
            viewModel.state()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::renderState)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        disposable.clear()
    }

    private fun renderState(state: SpecialsListState) {
        when (state) {
            SpecialsListState.Initial -> loadSpecialsList()
            SpecialsListState.Loading -> showLoading()
            is SpecialsListState.Error -> showError(state.text)
            is SpecialsListState.Content -> showContent(state)
        }
    }

    private fun showLoading() {
        binding?.apply {
            errorLayout.isVisible = false
            contentLayout.isVisible = false
            loadingLayout.isVisible = true
        }
    }

    private fun showError(errorText: String) {
        binding?.apply {
            errorLabel.text = errorText
            errorLayout.isVisible = true
            contentLayout.isVisible = false
            loadingLayout.isVisible = false
        }
    }

    private fun showContent(content: SpecialsListState.Content) {
        // Default display is deprecated but it works for this demo
        val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getRealMetrics(metrics)

        productAdapter.update(content.products, metrics.widthPixels)

        binding?.apply {
            errorLayout.isVisible = false
            contentLayout.isVisible = true
            loadingLayout.isVisible = false
        }
    }

    private fun loadSpecialsList() {
        viewModel.loadSpecialsList()
    }
}