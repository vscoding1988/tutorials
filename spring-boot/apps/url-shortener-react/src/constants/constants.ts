export const CONTENT = {
  APP_TITLE: 'URL Shortener',
  APP_FOOTER: '© 2025 URL Shortener',
  APP_DESCRIPTION:
          'The URL Shortener is a tool for shortening a long URL in order to provide better readability and more precise branded naming. A user clicking on the Short URL or scanning the QR Code will land on the exact same website like when entering the original URL in a browser.',
  BANNER_ALT: 'URL Shortener',
  FORM: {
    LONG_URL_LABEL: 'Enter the URL you want to shorten',
    LONG_URL_PLACEHOLDER: 'http://www.example-url.com/very-long-pathname',
    SHORT_URL_LABEL: 'Customize the shortened URL (optional)',
    SHORT_URL_PREFIX: 'https://sar.to/',
    SHORT_URL_PLACEHOLDER: 'YourShortURL',
    SUBMIT_BUTTON_TEXT: 'Create Short URL',
    ERROR_MESSAGE: 'Could not create the short URL. Check if the Short URL is already in use.',
  },
  TABLE: {
    CONTROLS: {
      SHOW: 'Show',
      PER_PAGE: 'per page',
      SEARCH_PLACEHOLDER: 'Search...',
    },
    PAGINATION: {
      PAGE_DESCRIPTION: (
              current: number,
              pageSize: number,
              totalHits: number
      ) => `Showing ${current} to ${pageSize} of ${totalHits} URLs`,
      PREVIOUS: 'Previous',
      NEXT: 'Next',
    },
    TABLE_LOADING: 'Loading...',
    TABLE_HEADERS: {
      SHORTENED_URL: 'Shortened URL',
      ORIGINAL_URL: 'Original URL',
      CREATION_DATE: 'Creation date',
      ACTIONS: 'Actions',
      CALLS: 'Calls',
    },
  },
} as const

const API_BASE_URL =
        'https://app-prod-sag-wcms-interview-weu-001.azurewebsites.net'

export const API_ENDPOINTS = {
  CREATE: `${API_BASE_URL}/api/short-url/create`,
  FIND: `${API_BASE_URL}/api/short-url/find`,
} as const
