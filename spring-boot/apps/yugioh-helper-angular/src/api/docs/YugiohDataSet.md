
# YugiohDataSet


## Properties

Name | Type
------------ | -------------
`id` | number
`setName` | string
`setCode` | string
`setRarity` | string
`setRarityCode` | string
`setPrice` | string

## Example

```typescript
import type { YugiohDataSet } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "setName": null,
  "setCode": null,
  "setRarity": null,
  "setRarityCode": null,
  "setPrice": null,
} satisfies YugiohDataSet

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as YugiohDataSet
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


