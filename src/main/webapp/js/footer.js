function checkFooterPosition() {
    const bodyHeight = document.body.scrollHeight;
    const viewportHeight = window.innerHeight;

    if (bodyHeight <= viewportHeight) {
        // If content is shorter than the viewport, fix the footer to the bottom
        document.body.style = "";
    } else {
        // If content is taller than the viewport, remove the fixed position
        document.body.style = "height: auto;";
    }
}

window.addEventListener('load', checkFooterPosition);
window.addEventListener('resize', checkFooterPosition);