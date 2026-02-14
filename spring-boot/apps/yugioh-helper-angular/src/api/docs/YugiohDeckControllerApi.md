# YugiohDeckControllerApi

All URIs are relative to *http://localhost:8080*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createDeck**](YugiohDeckControllerApi.md#createdeck) | **POST** /api/deck/create |  |
| [**deleteId**](YugiohDeckControllerApi.md#deleteid) | **DELETE** /api/deck |  |
| [**getDeckById**](YugiohDeckControllerApi.md#getdeckbyid) | **GET** /api/deck |  |
| [**getDecks**](YugiohDeckControllerApi.md#getdecks) | **GET** /api/deck/get-all |  |



## createDeck

> YugiohDeckCreationResponse createDeck(yugiohDeckCreationRequest)



### Example

```ts
import {
  Configuration,
  YugiohDeckControllerApi,
} from '';
import type { CreateDeckRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohDeckControllerApi();

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


## deleteId

> deleteId(id)



### Example

```ts
import {
  Configuration,
  YugiohDeckControllerApi,
} from '';
import type { DeleteIdRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohDeckControllerApi();

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
  YugiohDeckControllerApi,
} from '';
import type { GetDeckByIdRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohDeckControllerApi();

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
  YugiohDeckControllerApi,
} from '';
import type { GetDecksRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new YugiohDeckControllerApi();

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

