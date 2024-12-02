package se.fredrik.databashantering.Tools;

public class SimpleText {
    public static void introText(){
        System.out.println();
        System.out.println("1: Skriv ut allt i databasen");
        System.out.println("2: Välj en specifik data i databasen utifrån personID");
        System.out.println("3: Lägg in en ny person");
        System.out.println("4: Uppdatera en person");
        System.out.println("5: Ta bort en person");
        System.out.println("6: Avsluta och spara förändringar");


        System.out.println();
        System.out.print("Ditt val: ");
    }

    public static void wrongChoiceNumber(){
        System.out.println("Vänligen välj en siffra mellan 1-6");
    }
}
