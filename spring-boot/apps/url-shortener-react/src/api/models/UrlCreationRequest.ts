/* tslint:disable */
/* eslint-disable */
/**
 * Short Url API
 * Short Url API
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface UrlCreationRequest
 */
export interface UrlCreationRequest {
    /**
     * 
     * @type {string}
     * @memberof UrlCreationRequest
     */
    shortUrl?: string;
    /**
     * 
     * @type {string}
     * @memberof UrlCreationRequest
     */
    targetUrl?: string;
}

/**
 * Check if a given object implements the UrlCreationRequest interface.
 */
export function instanceOfUrlCreationRequest(value: object): value is UrlCreationRequest {
    return true;
}

export function UrlCreationRequestFromJSON(json: any): UrlCreationRequest {
    return UrlCreationRequestFromJSONTyped(json, false);
}

export function UrlCreationRequestFromJSONTyped(json: any, ignoreDiscriminator: boolean): UrlCreationRequest {
    if (json == null) {
        return json;
    }
    return {
        
        'shortUrl': json['shortUrl'] == null ? undefined : json['shortUrl'],
        'targetUrl': json['targetUrl'] == null ? undefined : json['targetUrl'],
    };
}

export function UrlCreationRequestToJSON(json: any): UrlCreationRequest {
    return UrlCreationRequestToJSONTyped(json, false);
}

export function UrlCreationRequestToJSONTyped(value?: UrlCreationRequest | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'shortUrl': value['shortUrl'],
        'targetUrl': value['targetUrl'],
    };
}

