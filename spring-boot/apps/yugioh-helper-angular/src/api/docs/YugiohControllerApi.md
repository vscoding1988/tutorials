# YugiohControllerApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDeck**](YugiohControllerApi.md#createdeck) | **POST** /api/create-deck |  |
| [**createPDF**](YugiohControllerApi.md#createpdf) | **POST** /api/create-pdf |  |
| [**deleteId**](YugiohControllerApi.md#deleteid) | **GET** /api/delete-deck |  |
| [**getDeckById**](YugiohControllerApi.md#getdeckbyid) | **GET** /api/get-deck |  |
| [**getDecks**](YugiohControllerApi.md#getdecks) | **GET** /api/get-decks |  |
| [**getImage**](YugiohControllerApi.md#getimage) | **GET** /api/image/small/{cardId} |  |



## createDeck

> YugiohDeckCreationResponse createDeck(yugiohDeckCreationRequest)



### Example

```ts
import {
  Configuration,
  YugiohControllerApi,
} from '';
import type { CreateDeckRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohControllerApi();

  const body = {
    // YugiohDeckCreationRequest
    yugiohDeckCreationRequest: ...,
  } satisfies CreateDeckRequest;

  try {
    const data = await api.createDeck(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **yugiohDeckCreationRequest** | [YugiohDeckCreationRequest](YugiohDeckCreationRequest.md) |  | |

### Return type

[**YugiohDeckCreationResponse**](YugiohDeckCreationResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## createPDF

> string createPDF(request)



### Example

```ts
import {
  Configuration,
  YugiohControllerApi,
} from '';
import type { CreatePDFRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohControllerApi();

  const body = {
    // PDFCreationRequest
    request: ...,
  } satisfies CreatePDFRequest;

  try {
    const data = await api.createPDF(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **request** | [](.md) |  | [Defaults to `undefined`] |

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## deleteId

> deleteId(id)



### Example

```ts
import {
  Configuration,
  YugiohControllerApi,
} from '';
import type { DeleteIdRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohControllerApi();

  const body = {
    // string
    id: id_example,
  } satisfies DeleteIdRequest;

  try {
    const data = await api.deleteId(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `string` |  | [Defaults to `undefined`] |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getDeckById

> DeckDTO getDeckById(id)



### Example

```ts
import {
  Configuration,
  YugiohControllerApi,
} from '';
import type { GetDeckByIdRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohControllerApi();

  const body = {
    // string
    id: id_example,
  } satisfies GetDeckByIdRequest;

  try {
    const data = await api.getDeckById(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `string` |  | [Defaults to `undefined`] |

### Return type

[**DeckDTO**](DeckDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getDecks

> Array&lt;DeckDTO&gt; getDecks()



### Example

```ts
import {
  Configuration,
  YugiohControllerApi,
} from '';
import type { GetDecksRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohControllerApi();

  try {
    const data = await api.getDecks();
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**Array&lt;DeckDTO&gt;**](DeckDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getImage

> string getImage(cardId)



### Example

```ts
import {
  Configuration,
  YugiohControllerApi,
} from '';
import type { GetImageRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohControllerApi();

  const body = {
    // number
    cardId: 789,
  } satisfies GetImageRequest;

  try {
    const data = await api.getImage(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **cardId** | `number` |  | [Defaults to `undefined`] |

### Return type

**string**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

