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
-	Fully Customized Components are used, inherited from the "View", I overwrite many components such as onMeasure( ), onLayout( ), and onDraw( ). The recursive process of Measure() is a commonly used process. Its technical implementation process is: measure( ) → onMeasure( ) → child.Measure( ), which implements a gradual calling process and carries the data through the data. The recursive call of the entire measure tree structure is implemented.  
  Des composants entièrement personnalisés sont utilisés, hérités de la "View", j'écrase de nombreux composants tels que onMeasure(), onLayout() et onDraw(). Le processus récursif de Measure() est un processus couramment utilisé. Son processus d'implémentation technique est : measure( ) → onMeasure( ) → child.Measure( ), qui implémente un processus d'appel progressif et transporte les données à travers les données. L'appel récursif de toute l'arborescence des mesures est implémenté.  
-	Create customized Android Service that runs in the background to remind users of upcoming to-do items./Créez un service Android personnalisé qui s'exécute en arrière-plan pour rappeler aux utilisateurs les tâches à effectuer à venir.  

# How to use
- minimalist theme  
  thème minimaliste
<div align=center>
<img src="https://user-images.githubusercontent.com/53630148/193410415-79bab4d6-0147-4ce7-bdaf-bae88b5d735f.gif">
</div>  

- Calendar  
  Calendrier
<div align=center>
<img src="https://user-images.githubusercontent.com/53630148/193412272-78d913d1-6602-45cb-bc67-23ba5614d387.gif">
</div>  

- Reminder List and Schedule items  
    All your reminders(unfinished items) are shown here, Click the circle on the left, if task is completed.  
    Click again if you made mistake  
    Long press to view the details of an item","Details of the schedule is shown here, Click on the upper right corner to see what has been done  
    Click the Edit button on the up right corner to choose the Reminder List that you want to delete.  
    Tous vos rappels (éléments inachevés) sont affichés ici, cliquez sur le cercle à gauche, si la tâche est terminée.  
    Cliquez à nouveau si vous avez fait une erreur  
    Appuyez longuement pour afficher les détails d'un élément", "Les détails du calendrier sont affichés ici, cliquez sur le coin supérieur droit pour voir ce qui a été fait  
    Cliquez sur le bouton Modifier dans le coin supérieur droit pour choisir la liste de rappels que vous souhaitez supprimer.  
  
   
<div align=center>
  <img src="https://user-images.githubusercontent.com/53630148/193413066-0cce816b-e9d1-487b-a44f-2887eaf591e2.gif">
  <img src="https://user-images.githubusercontent.com/53630148/193413554-7ebcbe7e-5206-4575-a5c9-ae198d1e9ca9.gif">
</div>  
<div align=center>
<img src="https://user-images.githubusercontent.com/53630148/193413756-8df3c3a5-8966-414e-983a-19498d67b061.gif">
</div>  

- timeline/chronologie  
  All the schedules created in the Calendar and all the reminders created in the reminder list will be shown here in the line of time for that day.  
  Tous les horaires créés dans le calendrier et tous les rappels créés dans la liste des rappels seront affichés ici dans la ligne de temps pour ce jour.
  <div align=center>
  <img src="https://user-images.githubusercontent.com/53630148/193808624-15254cc5-e86a-4bc7-972e-f5503fec8cfc.gif">
  </div>  

- Pomodoro Technique/Technique Pomodoro  
  it's a time counter for the Pomodoro Technique.  
  c'est un compteur de temps pour la Technique Pomodoro.  
  <div align=center>
  <img src="https://user-images.githubusercontent.com/53630148/193807528-7cca555e-2240-4dff-be07-448b80245dc1.gif">
  </div>  




    
    



# How to build
you can import into the Android Studio, the release version is updated.
