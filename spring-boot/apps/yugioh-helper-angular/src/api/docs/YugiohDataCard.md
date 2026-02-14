
# YugiohDataCard


## Properties

Name | Type
------------ | -------------
`id` | number
`name` | string
`type` | string
`humanReadableCardType` | string
`frameType` | string
`desc` | string
`race` | string
`nameEn` | string
`ygoprodeckUrl` | string
`cardSets` | [Array&lt;YugiohDataSet&gt;](YugiohDataSet.md)
`cardImages` | [Array&lt;YugiohDataImage&gt;](YugiohDataImage.md)
`cardPrices` | [Array&lt;YugiohDataPrice&gt;](YugiohDataPrice.md)

## Example

```typescript
import type { YugiohDataCard } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "name": null,
  "type": null,
  "humanReadableCardType": null,
  "frameType": null,
  "desc": null,
  "race": null,
  "nameEn": null,
  "ygoprodeckUrl": null,
  "cardSets": null,
  "cardImages": null,
  "cardPrices": null,
} satisfies YugiohDataCard

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as YugiohDataCard
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


