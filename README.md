
1. Tiktok Taxifare Distance Application

2. Project Description
This is the repositry for the TikTok Youth Camp 2022. Our Android application is able to register and users onto an online database(Firebase) and has also gelocating features with it(Geofire) with the use Google Maps. Aside from this, we have also created java functions to calculate the taxis prices based off the distance from several taxi companies like grab/comfortdelgro/uber etc.

Why do we need to store the data? simple, in the event that we require the need to do constant location tracking, we can always request locational updates to the app and the longitude and latitude and store the new values to the database. With this new data we can the recaluclate the chanigng distance. 

A large challenge we face was the integration of functionality with the ui/ux of android studio. Additionally, the debugging process has 2-3 times the layers of a convential program. It was hard to identify the issue and rememdy it.

3. How to Install and run the program
I would recommend cloning the whole repo and running it in android studio with your emulator/mobile device attached and then install the application onto the device.

In the case you would like to install from scratch:
1) check out the build.gradle(module) + build.gradle(project) file in android folder, import all the gradle files

2) In Themes.xml files, both of them, make sure the style is : <style name="Theme.Uberclone" parent="Theme.MaterialComponents.DayNight.NoActionBar">

3) Firebase + Geofire, this will cause some issues because this application is tied to my own database created for the project. If you want to similarly follow, I highly recommend you go to the Firebase mainframe and find out how to begin/setup from there. Geofire has a similar setup

4) Because we integrated the navigation bar to an empty activity, you will also need to download the icons into src->main->res->drawable in the project folder

4. How to Use the project
Its pretty self explanatory, if this is your first time, please do register an account in the login button from the navigation menu. Once registered, try to login with the same credentials. Once inside, click the location button at the top right to attain your current location and type in your destination and save the result. This will show the distance value between the 2 locations. Remember this value and key into the calculation feature.

5. Group Distribution:
Mervyn[Muffinc]- Geofire,Firebase,Googlemaps 
Cloey[dobirdrobert]- Distance Functionality
Apurva[Mishra-Apurva]- Distance Activity Page
Lim Ke En[keenlim]- Landingpage, Navigation Bar  
