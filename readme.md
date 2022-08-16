###Description
This project's main purpose is to emulate a plugin-like scenario which uses various Google APIs to perform tasks like keeping a watch on a user's drive folder and syncing changes to the disk.

###Setup
- Create a Google cloud project and enable the Drive API. Refer [steps](https://developers.google.com/workspace/guides/create-project).
- Create OAuth credentials to be used by the project. Refer [steps](https://developers.google.com/workspace/guides/create-credentials).
- Paste the `credentials.json` generated in step 3 and paste it in `src/main/java/resources`
- Assign the `DRIVE_FOLDER` variable with the `drive folder ID` which is required to be _watched_
- Set the `PAGE_SIZE` variable to the desired page size (_defaults to 10_)

