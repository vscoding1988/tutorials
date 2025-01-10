import {Configuration, ShortUrlApi} from "../api";

export const BACKEND_BASE_URL = 'http://localhost:8080';

const configuration = new Configuration({
  basePath: BACKEND_BASE_URL,
});

export const shortUrlApi = new ShortUrlApi(configuration);
