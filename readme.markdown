# HTML5 Games Example

This is an example project that shows how to integrate Universal Uclick's HTML5 Games in an Android WebView`.

## To get this running

You will need to update the following file:

    res/values/strings.xml

Open the project in your text editor or IDE and make sure to plug your Universal Uclick Client ID and Authorization Key in the `strings.xml` file. Then just build and run!

## In detail

The game engine loads itself via JavaScript into an `iframe` that the JavaScript creates on the page. This JavaScript is coded into `MainActivity.java`, which returns an HTML string with that JavaScript inside of it. That JavaScript gets customized in that same method based on a few things.

1. **Your Package Name**. This must be provided to Universal Uclick when you purchase an HTML5 Game package. (For example, this application's package name is `com.universaluclick.HTMLGamesExample`.)
2. **Your Universal Uclick Client ID**. This will be given to you by Universal Uclick.
3. **Your Authorization Key**. This will also be given to you by Universal Uclick.
4. **The desired game key**. In this app, those keys are located at `MainActivity.java`, or as follows:
    - **Crossword**: `crossword`
    - **Sudoku**: `sudoku`
    - **Word Roundup**: `wordroundup`
    - **Up and Down Words**: `upanddownwords`
    - **PlayFour**: `playfour`

That string of HTML is then loaded into a `WebView` using the method `onCreateView()`, as demonstrated in `HtmlGameFragment` in `MainActivity.java`.

## Questions?

Contact Universal Uclick Client Support if you need any assistance in implementing your HTML5 Games. If you have any other questions, please contact Universal Uclick's Sales department.
