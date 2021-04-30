# Week #2 Status Report

### Snapshot
Below is an image of this week's task board:

![image](https://media.discordapp.net/attachments/569804589373849649/837574086170902558/unknown.png?width=1202&height=676)

### Accomplishments
Our team had a hard time adjusting to the collaboration that GitHub requires. I helped our team go through all of our branches that had been stacking up commits and managed to successfully merge all of the branches without issues (only paymentmethods.html had a white page error that will soon be solved).

**Merge Branches + Rebase**
* [Justine's changes](https://github.com/nguyensjsu/sp21-172-team-a/commit/dcfa0f35dc7f36ee9dcb804e236be6bce2efee00)
* [Devansh's changes](https://github.com/nguyensjsu/sp21-172-team-a/commit/b128f476f546007455c8d596ea29ac9404c07940)
* [Jason's changes](https://github.com/nguyensjsu/sp21-172-team-a/commit/56d3821a4ef124782f7946d397d11dbbfc6557b7)

**Cleanup Commits**
* [solve .gitignore problems](https://github.com/nguyensjsu/sp21-172-team-a/commit/5bc4b7b7b4f6258481c1544de2ec3dfba3554724) that were causing problems with excessive files.
* https://github.com/nguyensjsu/sp21-172-team-a/commit/c3c0b40f184a4aed685e4e1c3bc00f07e31be52e
* https://github.com/nguyensjsu/sp21-172-team-a/commit/d4923d158ceb579c53fbe1d2c6f43bb7ffdeb396
* https://github.com/nguyensjsu/sp21-172-team-a/commit/6384450e26143abdd342feb5c12bdee582b74166
* https://github.com/nguyensjsu/sp21-172-team-a/commit/c6cbd63eccaca95cacc3f755e1b659d02655dbed


**Cashier's Application Backend + New Frontend:**

1) Merge index.html and starbucksmenu.html changes (IN PROGRESS) [Commit](https://github.com/nguyensjsu/sp21-172-team-a/blob/fad8dcfd292f7ea43b305f9888efba1e5fb647af/backend/spring-cashier/src/main/resources/templates/index.html)

In order to implement the features of selecting from the menu rather than just generating a random order through the cashier's application, I am attempting to merge the two HTML files so that the cashier's app can run cleanly on a single UI. This is still a work in progress as the feature requires a shopping cart feature to place the order. This was originally going to be implemented in JS, but I decided to move to Spring to simplify our filesystem and make it more accessible through other files.

2) [Javascript](https://github.com/nguyensjsu/sp21-172-team-a/blob/fad8dcfd292f7ea43b305f9888efba1e5fb647af/backend/spring-cashier/src/main/resources/static/js/store.js) and [CSS](https://github.com/nguyensjsu/sp21-172-team-a/blob/fad8dcfd292f7ea43b305f9888efba1e5fb647af/backend/spring-cashier/src/main/resources/static/css/index.css) changes
These were the CSS and JS files that made the HTML more interactive and pleasing to the eye.

### Challenges
As mentioned above, our team isn't very proficient in using Git. I worked with my team cleaning up our first week's errors with branching and commiting that led to outdated branches and merge conflicts. We managed to merge everything into one directory (backend) which allows to us run the application all together.

