const theme = {
    LIGHT: "light",
    DARK: "dark"
};

const scriptDir = new URL(".", document.currentScript.src);
const darkModeVector = new URL("../resource-files/bright-sun-light-svgrepo-com.svg", scriptDir).href;
const lightModeVector = new URL("../resource-files/dark-mode-night-moon-svgrepo-com.svg", scriptDir).href;


let currentTheme = theme.DARK;

const navbar = document.getElementById("navbar-top-firstrow");

if (navbar) {
    navbar.insertAdjacentHTML("beforeend", `
        <li style="margin-left:auto; margin-right:0.5rem; display:flex; align-items:center; justify-content:center;">
            <button id="themeButton">
                <img src=""
                     style="height:1.5rem; width:1.5rem;">
            </button>
        </li>
    `);
}

const saved = localStorage.getItem("theme");

if (saved) {
    currentTheme = saved;
}

updateTheme();

document.getElementById("themeButton").addEventListener("click", () => {
    currentTheme =
        currentTheme === theme.DARK
            ? theme.LIGHT
            : theme.DARK;

    localStorage.setItem("theme", currentTheme);
    console.log(currentTheme + " theme");

    updateTheme();
});


function updateTheme() {
    let root = document.documentElement.style;

    if (currentTheme == theme.DARK) {
        /* Text colors for body and block elements */
        root.setProperty('--body-text-color', '#f1f1f1');
        root.setProperty('--block-text-color', '#f1f1f1');

        /* Background colors for various elements */
        root.setProperty('--body-background-color', '#252525');
        root.setProperty('--section-background-color', '--body-background-color');
        root.setProperty('--detail-background-color', '#252525');
        root.setProperty('--code-background-color', '#222222');
        root.setProperty('--mark-background-color', '#1a1a1a');
        root.setProperty('--detail-block-color', '#161616');

        /* Colors for navigation bar and table captions */
        root.setProperty('--navbar-background-color', '#4D7A97');
        root.setProperty('--navbar-text-color', '#1d1d1d');

        /* Background color for subnavigation and various headers */
        root.setProperty('--subnav-background-color', '#292b2c');
        root.setProperty('--subnav-link-color', '#47688a');
        root.setProperty('--member-heading-background-color', '--subnav-background-color');

        /* Background and text colors for selected tabs and navigation items */
        root.setProperty('--selected-background-color', '#c07616');
        root.setProperty('--selected-text-color', '#ffffff');
        root.setProperty('--selected-link-color', '#4a698a');

        /* Background colors for generated tables */
        root.setProperty('--table-header-color', '#1f2020');
        root.setProperty('--even-row-color', '#1f1f1f');
        root.setProperty('--odd-row-color', '#222222');

        /* Text color for page title */
        root.setProperty('--title-color', '#507d9c');

        /* Text colors for links */
        root.setProperty('--link-color', '#5591bd');
        root.setProperty('--link-color-active', '#b66701');

        /* Table of contents */
        root.setProperty('--toc-background-color', '#1f1f1f');
        root.setProperty('--toc-highlight-color', '--subnav-background-color');
        root.setProperty('--toc-hover-color', '#2c2d2e');

        /* Snippet and pre colors */
        root.setProperty('--snippet-background-color', '#1f1f1f');
        root.setProperty('--snippet-text-color', '--block-text-color');
        root.setProperty('--snippet-highlight-color', '#9b7a58');
        root.setProperty('--pre-background-color', '--snippet-background-color');
        root.setProperty('--pre-text-color', '--snippet-text-color');

        /* Border colors for structural elements and user defined tables */
        root.setProperty('--border-color', '#252525');
        root.setProperty('--table-border-color', '#ffffff');

        /* Search input colors */
        root.setProperty('--search-input-background-color', '#242424');
        root.setProperty('--search-input-text-color', '#ffffff');
        root.setProperty('--search-input-placeholder-color', '#505050');

        /* Highlight color for active search tag target */
        root.setProperty('--search-tag-highlight-color', '#99993c');

        /* Copy button colors and filters */
        root.setProperty('--button-border-color', '#35373b');

        /* Colors for invalid tag notifications */
        root.setProperty('--invalid-tag-background-color', '#272727');
        root.setProperty('--invalid-tag-text-color', '#ffffff');

        // Specific behavior
        document.getElementById("themeButton").children[0].src = lightModeVector;

        const section = document.getElementById("help-keyboard-navigation");
        if (section) {
            section.querySelectorAll("kbd").forEach(element => {
                element.style.backgroundColor = "#252525";
            });
        }
    } else {
        /* Text colors for body and block elements */
        root.setProperty('--body-text-color', '#181818');
        root.setProperty('--block-text-color', '#181818');

        /* Background colors for various elements */
        root.setProperty('--body-background-color', '#ffffff');
        root.setProperty('--section-background-color', '--body-background-color');
        root.setProperty('--detail-background-color', '#ffffff');
        root.setProperty('--code-background-color', '#f5f5f5');
        root.setProperty('--mark-background-color', '#f7f7f7');
        root.setProperty('--detail-block-color', '#f4f4f4');

        /* Colors for navigation bar and table captions */
        root.setProperty('--navbar-background-color', '#4D7A97');
        root.setProperty('--navbar-text-color', '#ffffff');

        /* Background color for subnavigation and various headers */
        root.setProperty('--subnav-background-color', '#dee3e9');
        root.setProperty('--subnav-link-color', '#47688a');
        root.setProperty('--member-heading-background-color', '--subnav-background-color');

        /* Background and text colors for selected tabs and navigation items */
        root.setProperty('--selected-background-color', '#f8981d');
        root.setProperty('--selected-text-color', '#253441');
        root.setProperty('--selected-link-color', '#4a698a');

        /* Background colors for generated tables */
        root.setProperty('--table-header-color', '#ebeff4');
        root.setProperty('--even-row-color', '#ffffff');
        root.setProperty('--odd-row-color', '#f0f0f2');

        /* Text color for page title */
        root.setProperty('--title-color', '#2c4557');

        /* Text colors for links */
        root.setProperty('--link-color', '#437291');
        root.setProperty('--link-color-active', '#bb7a2a');

        /* Table of contents */
        root.setProperty('--toc-background-color', '#f8f8f8');
        root.setProperty('--toc-highlight-color', '--subnav-background-color');
        root.setProperty('--toc-hover-color', '#e9ecf0');

        /* Snippet and pre colors */
        root.setProperty('--snippet-background-color', '#f2f2f4');
        root.setProperty('--snippet-text-color', '--block-text-color');
        root.setProperty('--snippet-highlight-color', '#f7c590');
        root.setProperty('--pre-background-color', '--snippet-background-color');
        root.setProperty('--pre-text-color', '--snippet-text-color');

        /* Border colors for structural elements and user defined tables */
        root.setProperty('--border-color', '#e6e6e6');
        root.setProperty('--table-border-color', '#000000');

        /* Search input colors */
        root.setProperty('--search-input-background-color', '#ffffff');
        root.setProperty('--search-input-text-color', '#000000');
        root.setProperty('--search-input-placeholder-color', '#909090');

        /* Highlight color for active search tag target */
        root.setProperty('--search-tag-highlight-color', '#ffff66');

        /* Copy button colors and filters */
        root.setProperty('--button-border-color', '#b0b8c8');

        /* Colors for invalid tag notifications */
        root.setProperty('--invalid-tag-background-color', '#ffe6e6');
        root.setProperty('--invalid-tag-text-color', '#000000');

        // Specific behavior
        document.getElementById("themeButton").children[0].src = darkModeVector;

        const section = document.getElementById("help-keyboard-navigation");
        if (section) {
            section.querySelectorAll("kbd").forEach(element => {
                element.style.backgroundColor = "";
            });
        }
    }
}