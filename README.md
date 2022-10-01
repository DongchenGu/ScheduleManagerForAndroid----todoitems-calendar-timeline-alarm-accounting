# ScheduleManagerForAndroid----todoitems-calendar-timeline-alarm-accounting

<div align=center>
<img src="https://user-images.githubusercontent.com/53630148/193409294-eb907c2d-078a-4e0b-ac90-6e9590bbb28f.png">
</div>

# Description
It's an Android application based on Java language and Android SQlite database. Aims to improve the user's work efficiency and facilitate the user's itinerary planning. 
It integrates the Calendar and daily Timeline, which can automatically classify the to-do items created by the user, as well as automatic reminders in the background. 
It could also synchronizes user data to the server(we don't maintain the server anymore). 
-	Use multiple fragments for each activity to provide beautiful interface and Utilize Event Bus and Provider to manage the status between parent widgets and children widgets.
-	Fully Customized Components are used, inherited from the "View", I overwrite many components such as onMeasure( ), onLayout( ), and onDraw( ). The recursive process of Measure() is a commonly used process. Its technical implementation process is: measure( ) → onMeasure( ) → child.Measure( ), which implements a gradual calling process and carries the data through the data. The recursive call of the entire measure tree structure is implemented.
-	Create customized Android Service that runs in the background to remind users of upcoming to-do items.

# How to use


# How to build
you can import into the Android Studio, the release version is updated.
