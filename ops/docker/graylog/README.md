# Graylog
## Links
- [Homepage](https://www.graylog.org/)
- [Comparison: Open Source vs Pro version](https://www.graylog.org/products/open-source-vs-enterprise)
- [Docker Compose installation](https://github.com/Graylog2/docker-compose/tree/main/enterprise)
## Installation
For local tests we can download the free version and start it with

```shell
docker-compose up
```
The service will start on port [:9000](http://localhost:9000), when we enter the page we will see a login page.
To login use admin/<password you have hashed in .env file>
