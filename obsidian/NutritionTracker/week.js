const pathToConfig = "Config"
const nutrientConfig = getNutrientTrackerConfig(pathToConfig);
const nutrients = getNutritionForWeek();

dv.header(2, "Week overview");

renderSummary(nutrients, nutrientConfig);
renderNutrients(nutrients, nutrientConfig);

function renderSummary(array, config) {
  let summary = sumArray(array, config);

  dv.header(3, "Nutrition Average");
  dv.list(toSummaryList(summary, array.length));
}

function renderNutrients(array, config) {
  dv.header(3, "Nutrition by Day");

  let tableValues = transformToTableValues(array, config);
  dv.table(config.header, tableValues);
}

function getNutritionForWeek() {
  let result = [];
  let path = dv.current().file.folder + "/Days";

  for (let day of dv.pages('"'+path+'"')) {
    let dayNutrients = {};

    for (const [key, value] of Object.entries(day)) {
      if (key !== "file") {
        dayNutrients[key] = value;
      }
    }

    dayNutrients.name = day.file.name;
    result.push(dayNutrients);
  }

  return result;
}

// Helper
function getNutrientTrackerConfig(path) {
  return dv.page(path).nutrientTracker;
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

  return result;
}

function toSummaryList(object,days) {
  let list = [];

  for (const [key, value] of Object.entries(object)) {
    if (key !== "name") {
      list.push(key + ": " + round(value/days));
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
