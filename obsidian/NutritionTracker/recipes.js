const pathToConfig = "Config"
const nutrientConfig = getNutrientTrackerConfig(pathToConfig);
const nutrients = getNutrientsForPage(dv.current(), nutrientConfig);

renderSummary(nutrients, nutrientConfig);

function renderSummary(array, config) {
  let summary = sumArray(array, config);

  dv.header(2, "Nutrients");
  dv.list(toSummaryList(summary));
}

function getNutrientsForPage(page, config) {
  let result = [];

  for (const listItem of page.file.lists) {
    if(listItem.header.subpath === config.recipesHeader){
      let nutrition = parseListItem(listItem);

      if(nutrition){
        result.push(nutrition);
      }
    }
  }

  return result;
}

// Helper
function getNutrientTrackerConfig(path) {
  return dv.page(path).nutrientTracker;
}

function parseListItem(meal) {
  let parsedText = parseText(meal.text);
  let linkToMeal = dv.page(meal.outlinks[0]);

  if(!parsedText || !linkToMeal){
    return parsedText;
  }

  let result = {};

  let multiplier = parsedText.weight / linkToMeal.weight;

  for (const [key, value] of Object.entries(linkToMeal)) {
    if(key !== "file"){
      let mealValue = value || 0;
      result[key] = round(mealValue * multiplier);
    }
  }

  result.name = linkToMeal.file.name;

  return result;
}

function parseText(text){
  let cleanText = text.trim();
  let regex = /(\d*)\D*(\[\[.[^\]]*]])\D*(\d*).*/;
  let result = cleanText.match(regex);

  if(result.length !== 4){
    return null;
  }

  return {
    weight: parseInt(result[1] || result[3]),
    name: result[2]
  }
}

function sumArray(array) {
  let result = {};

  for (let item of array) {
    for (const [key, value] of Object.entries(item)) {
      result[key] = (result[key] || 0) + (value || 0);
    }
  }

  for (const [key, value] of Object.entries(result)) {
    result[key] = round(value);
  }

  result.name = "Summary";

  return result;
}

function toSummaryList(object) {
  let list = [];

  for (const [key, value] of Object.entries(object)) {
    if (key !== "name") {
      list.push(key + ": " + round(value));
    }
  }

  return list;
}

function round(number) {
  if (typeof number === 'number') {
    return Math.round(number * 10000) / 10000;
  }

  return number;
}
