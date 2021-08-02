# README

## Random Notes
- I did not have time to add dependency injection via Dagger, so the ViewModelFactory accesses singletons instead. A real app would definitely use some form of DI, likely Dagger/Hilt.

- Google's `FlexboxLayoutManager` seemed to do exactly what I needed according to the requirements. I had originally implemented it using span count values for the `GridLayoutManager` (since each canvas unit could be treated as a span), but ran into difficulties centering cards that didn't fit the full width. It would certainly have been possible, but `FlexboxLayoutManager` seemed far easier.

- The specifications did not indicate the ideal UI behavior when the product frames are too small to hold the contents, and as I was working on this during the weekend I did not ask for clarification. It wasn't clear from the requirements if I should have some minimum card size, so I didn't do so. I proceeded under the assumption the prices and name were most important, and set the photo to shrink to allow the former to be fully visible. This still doesn't produce entirely readable cards on my small-screen emulators, so in a real app some discussion with the design team would be appropriate. A possible option would be to use the autosizing option of `TextView`s (i.e. `android:autoSizeMinTextSize`) to dynamically resize the text to be hopefully legible (and non-overlapping) on very small cards.

- The following sentence from the requirements wasn't entirely clear to me, but I interpreted it to mean cards should be added left-to-right and wrap to a newline when the next card cannot fit, with any non-full rows centered horizontally. `If the combined width of the product frames exceeds the full width of the phone move the product frame to the next line and center any frames that do not fit in the full width frame.`

- I did not use any modules, but moving the specials API to its own module would be a good idea in a real app. The specials UI experience could also be moved, depending on the project's structure.
