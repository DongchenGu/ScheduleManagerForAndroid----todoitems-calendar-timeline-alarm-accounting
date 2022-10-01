# ScheduleManagerForAndroid----todoitems-calendar-timeline-alarm-accounting(english/french)

<div align=center>
<img src="https://user-images.githubusercontent.com/53630148/193409294-eb907c2d-078a-4e0b-ac90-6e9590bbb28f.png">
</div>

# Description
It's an Android application based on Java language and Android SQlite database. Aims to improve the user's work efficiency and facilitate the user's itinerary planning. 
It integrates the Calendar and daily Timeline, which can automatically classify the to-do items created by the user, as well as automatic reminders in the background. 
It could also synchronizes user data to the server(we don't maintain the server anymore).  

C'est une application Android basée sur le langage Java et la base de données Android SQLite. Vise à améliorer l'efficacité du travail de l'utilisateur et à faciliter la planification de l'itinéraire de l'utilisateur.
Il intègre le calendrier et la chronologie quotidienne, qui peuvent classer automatiquement les tâches créées par l'utilisateur, ainsi que des rappels automatiques en arrière-plan.
Il pourrait également synchroniser les données de l'utilisateur avec le serveur (nous ne maintenons plus le serveur).

-	Use multiple fragments for each activity to provide beautiful interface and Utilize Event Bus and Provider to manage the status between parent widgets and children widgets./Utilisez plusieurs fragments pour chaque activité pour fournir une belle interface et utilisez Event Bus and Provider pour gérer le statut entre les widgets parents et les widgets enfants.  
-	Fully Customized Components are used, inherited from the "View", I overwrite many components such as onMeasure( ), onLayout( ), and onDraw( ). The recursive process of Measure() is a commonly used process. Its technical implementation process is: measure( ) → onMeasure( ) → child.Measure( ), which implements a gradual calling process and carries the data through the data. The recursive call of the entire measure tree structure is implemented./Des composants entièrement personnalisés sont utilisés, hérités de la "View", j'écrase de nombreux composants tels que onMeasure(), onLayout() et onDraw(). Le processus récursif de Measure() est un processus couramment utilisé. Son processus d'implémentation technique est : measure( ) → onMeasure( ) → child.Measure( ), qui implémente un processus d'appel progressif et transporte les données à travers les données. L'appel récursif de toute l'arborescence des mesures est implémenté.  
-	Create customized Android Service that runs in the background to remind users of upcoming to-do items./Créez un service Android personnalisé qui s'exécute en arrière-plan pour rappeler aux utilisateurs les tâches à effectuer à venir.  

# How to use
- minimalist theme  
<div align=center>
<img src="https://user-images.githubusercontent.com/53630148/193410415-79bab4d6-0147-4ce7-bdaf-bae88b5d735f.gif">
</div>



# How to build
you can import into the Android Studio, the release version is updated.
