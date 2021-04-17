
## Lesson 1.1: Acquiring Software

More than likely, you're reading this online, in a web browser now. You can click around to see other files, make small modifications. This is a very nice feature of *Github*, the website where we we keep our software.

However, to run the code, and make bigger changes, you'll want the software on your computer.

*Git* is the name of the tool we use to move software between *Github*'s servers and our development computers.

As a one-time, first step, we will use the `clone` operation to do a first-time acquisition of software from github. Later, we will use other commands to `push` and `pull` changes from our local `repository` of software.

Motivation: In a team-based software development environment, you rarely start writing all your code from sractch (in fact, exactly one student does this once per build season). More frequrently, you are starting your development _based on_ the work someone else already did. You take their content, modify it, add your own things, and submit it back for the next person to work on. Multiply this by 10+ people, and you've got a team developing a singular codebase! THe reason we need tools like Git and Github are to ensure that all people can contribute their changes without "stepping" on each other, using different computers. It additionaly ensures that, as a team, we have a singluar notion of what the "correct" or "most recent" software is. All of this is key to ensuring the software we provide is high-quality, and defect-free.

Perform the following steps:

1. Open the *windows file exploere* 

![](doc/in_file_explorer.png)

2. Create the folder `C:\Users\<username>\SwDev\`, if it does not yet exist. Open that folder in the file browser. 

<details>
<summary> Hint</summary>

Here's one set of steps to accomplish that:

Select the `C:\` drive:

![](doc/c_drive.png)

Select the `Users` folder, followed by your username:

![](doc/c_users_folder.png)

Right Click in the empty space, hover over `New`, and make a new folder:

![](doc/make_new_folder.png)

Type in the approprate name for the new folder:

![](doc/new_folder_name.png)

</details>


3. Right click in the empty space in the folder to get the context menu, and select `GitExt Clone`.

![](doc/gitext_clone.png)

4. Paste the following *URL* into the top "Repository to clone" box: `https://github.com/RobotCasserole1736/OffseasonTraining`

5. All other default options are fine. Click the `Clone` button.

You may be prompted for your github username and password - enter it if needed.

Afterward, you should see a new folder named `OffseasonTraining`.

Huzzah! You now have some software on your computer, which you can begin to edit.

