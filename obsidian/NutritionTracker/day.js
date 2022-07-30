const valueKeys = ["name","carb", "protein", "fat", "leucine", "kcal"];
const header = ["Name","Carb", "Protein", "Fat", "Leucine", "Kcal"];
const nutrients = getNutrientsForDay();

renderSummary(nutrients);
renderNutrients(nutrients);

function renderSummary(array) {
  let daySummary = sumArray(array);

  dv.header(2, "Summary");
  dv.list(toSummaryList(daySummary));
}

function renderNutrients(array) {
  dv.header(2, "Nutrients");

  let tableValues = transformToTableValues(array);
  dv.table(header, tableValues);
}

function getNutrientsForDay() {
  let result = [];

  for (const listItem of dv.current().file.lists) {
    result.push(parseListItem(listItem));
  }

  return result;
}

// Helper
function parseListItem(meal) {
  let linkToMeal = dv.page(meal.outlinks[0]);
  let gramEaten = meal.text.split("]] ")[1].trim();
  let multiplier = parseInt(gramEaten) / linkToMeal.gram;

  let result = {};

  for (let key of valueKeys) {
    let mealValue = linkToMeal[key] || 0;
    result[key] = round(mealValue * multiplier);
  }

  result.name = linkToMeal.file.name;

  return result;
}

function transformToTableValues(array) {
  let result = [];

  for (let item of array) {
    result.push(transformToTableValue(item));
  }

  return result;
}

function transformToTableValue(object) {
  let row = [];
  console.info(object)
  for (let key of valueKeys) {
    row.push(object[key] || 0);
  }

  return row;
}

function sumArray(array) {
  let result = {};

  for (let item of array) {
    for (const key of valueKeys) {
      let value = result[key] || 0;
      result[key] = value + item[key];
    }
  }

  result.name = "Summary";

  return result;
}

function toSummaryList(object) {
  let list = [];

  for (let index in header) {
    if(index !== "0"){
      let value =object[valueKeys[index]];
      list.push(header[index] + ": " + round(value));
    }
  }

  return list;
}

function round(number){
  if(typeof number === 'number'){
    return Math.round(number*10000)/10000;
  }
  return number;
}
