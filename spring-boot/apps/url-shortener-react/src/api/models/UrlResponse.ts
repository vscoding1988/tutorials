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
import type { UrlDTO } from './UrlDTO';
import {
    UrlDTOFromJSON,
    UrlDTOFromJSONTyped,
    UrlDTOToJSON,
    UrlDTOToJSONTyped,
} from './UrlDTO';

/**
 * 
 * @export
 * @interface UrlResponse
 */
export interface UrlResponse {
    /**
     * 
     * @type {number}
     * @memberof UrlResponse
     */
    count?: number;
    /**
     * 
     * @type {Array<UrlDTO>}
     * @memberof UrlResponse
     */
    urls?: Array<UrlDTO>;
}

/**
 * Check if a given object implements the UrlResponse interface.
 */
export function instanceOfUrlResponse(value: object): value is UrlResponse {
    return true;
}

export function UrlResponseFromJSON(json: any): UrlResponse {
    return UrlResponseFromJSONTyped(json, false);
}

export function UrlResponseFromJSONTyped(json: any, ignoreDiscriminator: boolean): UrlResponse {
    if (json == null) {
        return json;
    }
    return {
        
        'count': json['count'] == null ? undefined : json['count'],
        'urls': json['urls'] == null ? undefined : ((json['urls'] as Array<any>).map(UrlDTOFromJSON)),
    };
}

export function UrlResponseToJSON(json: any): UrlResponse {
    return UrlResponseToJSONTyped(json, false);
}

export function UrlResponseToJSONTyped(value?: UrlResponse | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'count': value['count'],
        'urls': value['urls'] == null ? undefined : ((value['urls'] as Array<any>).map(UrlDTOToJSON)),
    };
}

