/**
 * Called when button is clicked, will increase/decrease the counter
 *
 * @param reverse if true the counter will be decreased
 */
function onButtonCountClick(reverse) {
  const counter = document.getElementsByClassName("js-button-count");

  if (counter.length > 0) {
    let currentCount = Number.parseInt(counter[0].textContent);

    counter[0].textContent = (currentCount + (reverse ? -1 : 1)).toString();
  }
}
