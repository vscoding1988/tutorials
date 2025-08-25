# Akamai Log Merger

Akamai is sending logs in multiple files, to azure storage. This app:

- Downloads the files from storage
- Merges them into a single file
- zips the file
- Uploads the file back to storage

Currently the merging is only supported for JSON files.
