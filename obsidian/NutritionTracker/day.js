const pathToConfig = "Config"
const nutrientConfig = getNutrientTrackerConfig(pathToConfig);
const nutrients = getNutrientsForPage(dv.current(), nutrientConfig);

renderSummary(nutrients, nutrientConfig);
renderNutrients(nutrients, nutrientConfig);

function renderSummary(array, config) {
  let summary = sumArray(array, config);

  dv.header(2, "Nutrition Overview");
  dv.list(toSummaryList(summary));
}

function renderNutrients(array, config) {
  dv.header(2, "Nutrition by Meal");

  let tableValues = transformToTableValues(array, config);
  dv.table(config.header, tableValues);
}

function getNutrientsForPage(page, config) {
  let result = [];

  let headerLookup = summarizeByMeal(page, config);

  for (const [key, value] of Object.entries(headerLookup)) {
    let summedArray = sumArray(value);
    summedArray.name = key;
    result.push(summedArray);
  }

  return result;
}

function summarizeByMeal(page, config) {
  let headerLookup = {};

  for (const listItem of page.file.lists) {
    let headerName = listItem.header.subpath;
    if (config.dayHeaders.indexOf(headerName) !== -1) {
      let nutrition = parseListItem(listItem);

      if (nutrition) {
        if (!headerLookup[headerName]) {
          headerLookup[headerName] = [];
        }

        headerLookup[headerName].push(nutrition);
      }
    }
  }
  return headerLookup;
}

// Helper
function getNutrientTrackerConfig(path) {
  return dv.page(path).nutrientTracker;
}

function parseListItem(meal) {
  let linkToMeal = dv.page(meal.outlinks[0]);

  if (!linkToMeal) {
    return null;
  }

  let result = {};

  let multiplier = getMultiplier(meal.text, linkToMeal.weight);

  for (const [key, value] of Object.entries(linkToMeal)) {
    if (key !== "file") {
      let mealValue = value || 0;
      result[key] = round(mealValue * multiplier);
    }
  }

  result.name = linkToMeal.file.name;

  return result;
}

function getMultiplier(text, weight) {
  let multiplier = 1;
  let split = text.split("]] ");

  if (split.length === 2) {
    multiplier = parseInt(split[1]) / weight;
  }

  return multiplier;
}

function transformToTableValues(array, config) {
  let result = [];

  for (let item of array) {
    result.push(transformToTableValue(item, config));
  }

  return result;
}

function transformToTableValue(object, config) {
  let row = [];

  for (let key of config.headerKey) {
    row.push(object[key] || 0);
  }

  return row;
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
