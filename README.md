# DueDateCalculator
The program reads the currently reported problems (bugs) from an issue tracking system and calculates the due date following the rules below:
- Working hours are 9 AM to 5 PM every working day (Monday through Friday)
- The program does not deal with holidays, which means that a holiday on a Thursday is still considered as a working day by the program. Also, a working Saturday will be considered as a nonworking day by the system.
- The turnaround time is given in working hours, which means for example, that 2 days are 16 hours. As an example: if a problem was reported at 2:12 PM on Tuesday, then it is due by 2:12 PM on Thursday.
- A problem can only be reported during working hours, which means that all submit date values fall between 9 AM and 5 PM.
