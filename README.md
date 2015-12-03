# android-instagram

## Overview

This is a Instagram Client Project.
 
Submitted by: **Misha Bosin**

## User Stories

### Day 1

Time spent: **7** hours total

* [x] User can scroll through the popular posts from Instagram.
* [x] Show the following details: Graphic, Caption, Username, User profile image
* [x] Relative timestamp, Like count
* [x] Display each user profile image as a circle.
* [x] Display a nice default placeholder graphic for each image during loading.
* [x] Display each post with the same style and proportions as the real Instagram.

### Day 2

Time spent: **8** hours total

* [x] Connect the app with the Instagram API and get real time data using async-http-client library.
* [x] Show the last 2 comments for each photo.
* [x] User can view all comments for a post within a separate activity.
* [x] User can share an image to their friends or email to themselves.

The following user stories are optional:
* [ ] Use the Butterknife library to remove all findViewById(...) calls.
* [ ] Robust error handling, check if internet is available, handle network failures.

### Day 3

Time spent: **10** hours total

* [x] User can login to Instagram using OAuth login.
* [x] User can view their own feed.
* [x] User can search for a user by username.
* [x] User can search for a tag.
* [x] On the search screen, there will now be 2 tabs corresponding to "USERS" and "TAGS".
* [x] When performing a search, the user can switch between the "USERS" tab and the "TAGS" tab and see search results for each one.

The following user stories are optional:
* [ ] Include a ProgressBar during network loading.
* [ ] User can get a grid of photos by clicking on a search result (i.e. a user or tag).

### Day 4

Time spent: **100** hours total

* [x] Add pull-to-refresh for the home feed with SwipeRefreshLayout.
* [x] User can open the Instagram client offline and see last loaded feed.
* [x] Each media item is persisted into SQlite and can be displayed from the local DB.
* [ ] Create a background service to make the network request, load items into the DB and populate the view (for the user's home feed).

The following user stories are optional:
* [ ] Add the ability for users to comment on a post.

## Video Walkthrough

  ![Imgur](http://i.imgur.com/i5baxfN.gif)

## Notes

* Learned a lot about the rendering properties, fragments, basic networking, menu tabs

## License

    Copyright 2015 Misha Bosin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
