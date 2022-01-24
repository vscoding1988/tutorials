# Post Mortem
## Goals
Post Mortem is an approach for a debrief of an accident. The goals are the learning from accidents, 
by providing standardize procedure for documenting them.
- recreate the timeline of the accident
- recreate the steps which were taken and their results
- document the solution
- document lessons learned
- create action items for preventing/better handling of the accidents in the future

## 4 Questions 
Each Post Mortem should answer the following questions:
- What has happened?
- Why did it happened?
- How responded to and fixed the error?
- What can be done to prevent similar accidents?

## Example
Remark: _To created blameless Post Mortem some organisations are using custom names, or the job description (f.e. Dev7, Tester2, ect.). I like this approach and suggest, to try it out_

Incident: When uploading a file, the name of the file were not persisted correctly, all files got the name "file" and lost their extension.
How discovered?: Dev1 added an E2E test for new feature and saw the error while running it.
Impact: sever, user would not be able to execute new file, because of missing extensions.

Timeline:
1) **10.10 14:00** Bug was committed, as part of Story ST-123<br>
            -> there is a Unit test for this case, but because the file was mocked, it detected any errors<br>
            -> there is an Integration test for this case, but the check includes only the status check<br>
2) **15.10 13:00** Bug was deployed to production
3) **18.10 11:00** Dev1 has run the E2E test and saw the error on dev system <br>
            -> because there were multiple changes in the Frontend from Dev2, he did nothing and tested his feature manually
4) **19.10 08:00** Dev1 started to check E2E and fix it.
5) **19.10 10:00** after not being able to find the error, he contacted Dev2
6) **19.10 10:20** Dev1 & Dev2 excluded the error in the frontend
7) **19.10 11:00** Dev1 found the error in the backend<br>
            -> Mock in the unit test was improved<br>
            -> integration test is now checking all the file properties and the file persisted in database
8) **19.10 11:20** Dev1 and informed SME & PO about the necessity of a hotfix deployment
9) **19.10 12:00** hotfix was deployed
10) **19.10 14:00** Post Mortem was created
11) **20.10 08:30** Post Mortem meeting

Action Items
- Integration of E2E to the pipeline execution (ST-212)
- step 8 should be executed immediately, so they can better prepare for it. (the learning will be announced in the weekly)
- Create test case for checking correct file metadata (will be discussed in additional meeting)
  - is this necessary? We have already unit/integration and E2E test which are covering the Issue 


