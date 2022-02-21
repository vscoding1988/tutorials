# Log Queries

[Azure Docs](https://docs.microsoft.com/en-us/azure/azure-monitor/logs/get-started-queries)
## Render Logs as graph
This query is useful for keep an eye on user interactions (f.e. login, downloads, purchases, ect)
```kusto
AppTraces 
| where Message == "Operation: customer login successful"
| summarize event_count = count() by bin(TimeGenerated, 5m)
| render timechart 
```
![Loging_Summarize.png](images/Loging_Summarize.png)
This query is fine but has a big downside, the missing values are skipped, dependent on your use case you might want to 
have them displayed, then you can use [make-series](https://docs.microsoft.com/en-us/azure/data-explorer/kusto/query/make-seriesoperator) operator.
```kusto
AppTraces 
| where Message == "Operation: customer login successful"
| make-series logins = count() default=0 on TimeGenerated step 5m by Message
| render timechart 
```
![Loging_Series.png](images/Loging_Series.png)

One of the downsides of using `make-series` are false positives inside the Dashboards.
![Loging_Series_Warn.png](images/Loging_Series_Warn.png)

## Parse Log in fields
We have a log message, which contains of multiple relevant values, we can use the split mechanic:
```text
Price push for product '1223' (AUT) successful oldPrice='120' newPrice='130'
```
```kusto
AppTraces 
| where Message has "Price push for product" 
| parse Message with * "Price push for product '" ProductNumber:string "' (" Country ") " Status " oldPrice='"OldPrice"' newPrice='"NewPrice"'"crap
| project TimeGenerated, ProductNumber, Country, Status, OldPrice, NewPrice
```
This will generate the following log.

| TimeGenerated              | ProductNumber | Country | Status     | OldPrice | NewPrice |
|:---------------------------|:--------------|:--------|:-----------|:---------|:---------|
| 12/1/2021, 9:45:03.580 AM  | 1223          | AUT     | successful | 120      | 130      |

