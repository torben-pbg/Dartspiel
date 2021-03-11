import sum.kern.*;
/**
 * Das Dartspiel
 * @author Torben Piepenburg
 * @version 10.1.3
 */
public class MeinProgramm
{
    // Objekte
    Bildschirm derBildschirm;
    Buntstift meinStift;
    Maus dieMaus;
    Tastatur dieTastatur;
    // Konstruktor

    public MeinProgramm()
    {
        derBildschirm = new Bildschirm(1280,720);
        meinStift = new Buntstift();
        dieMaus = new Maus();
        dieTastatur = new Tastatur();
    }

    // Dienste
    public void zeichnePfeil()
    {
        //Ausgangsbedingung festlegen
        meinStift.normal();
        meinStift.runter();

        //Pfeil zeichnen
        meinStift.bewegeUm(100);
        meinStift.hoch();
        meinStift.bewegeUm(5);
        meinStift.setzeFarbe(Farbe.BLAU);
        meinStift.zeichneKreis(5);
        meinStift.setzeFarbe(Farbe.SCHWARZ);

        meinStift.hoch();
    }

    public void loeschePfeil()
    {
        //Ausgangsbedingung festlegen
        meinStift.radiere();
        meinStift.runter();

        //Pfeil löschen
        meinStift.zeichneKreis(5);
        meinStift.bewegeUm(-5);        
        meinStift.bewegeUm(-100);        

        meinStift.hoch();
        meinStift.normal();
    }

    public void fuehreAus()
    {
        derBildschirm.setzeFarbe(4);              
        //Dartscheibe
        //Mittelpunkt: 1000px, 240px
        //Endpunkte: y=140px, y=340px
        meinStift.bewegeBis(1000, 240);
        meinStift.setzeFuellMuster(Muster.GEFUELLT);
        meinStift.setzeFarbe(Farbe.ROT);
        meinStift.zeichneKreis(100);
        meinStift.setzeFarbe(Farbe.SCHWARZ);
        meinStift.zeichneKreis(80);
        meinStift.setzeFarbe(Farbe.ROT);
        meinStift.zeichneKreis(60);
        meinStift.setzeFarbe(Farbe.SCHWARZ);
        meinStift.zeichneKreis(40);
        meinStift.setzeFarbe(Farbe.ROT);
        meinStift.zeichneKreis(20);


        //"Watermark"
        meinStift.setzeFarbe(Farbe.WEISS);
        meinStift.bewegeBis(500, 650);
        meinStift.schreibeText("Drücke eine beliebige Taste um zu starten!");
        meinStift.bewegeBis(1150, 710);
        meinStift.schreibeText("by Torben Piepenburg");
        meinStift.setzeFarbe(Farbe.SCHWARZ);

        do{
            //Tastatur zum Starten druecken
            if (dieTastatur.wurdeGedrueckt())
            {
                //Analyse vom Pfeil davor löschen
                meinStift.hoch();
                meinStift.bewegeBis(950, 390);          
                meinStift.radiere();
                meinStift.schreibeText("Daneben");
                meinStift.bewegeBis(950, 390);
                meinStift.schreibeText("Treffer!");  

                //Stift zum Startpunkt bewegen und Pfeil zeichnen
                meinStift.bewegeBis(100, 200);
                meinStift.dreheBis(10);
                this.zeichnePfeil();

                //Pfeil fallen lassen bis Maus gedrueckt
                while (!dieMaus.istGedrueckt())
                {
                    //alten Pfeil löschen
                    this.loeschePfeil();

                    //Stift bewegen
                    meinStift.hoch();
                    meinStift.dreheUm(-100);
                    meinStift.bewegeUm(0.5);                                   
                    meinStift.dreheUm(100);
                    meinStift.runter();

                    //neuen Pfeil zeichnen
                    this.zeichnePfeil();

                    //Wenn Pfeil aus dem Bildschirm "gefallen": Löschen zum Startpunkt und neu zeichnen
                    if (meinStift.vPosition() >= 720)
                    {
                        this.loeschePfeil();                        
                        meinStift.bewegeBis(100, 200);
                        this.zeichnePfeil();
                    }

                } 

                //Pfeil drehen
                while (dieMaus.istGedrueckt())
                {                  
                    this.loeschePfeil();

                    //Drehen
                    meinStift.hoch();
                    meinStift.dreheUm(0.05);
                    meinStift.runter();

                    this.zeichnePfeil();
                }

                //Pfeil zur Dartscheibe fliegen lassen
                do
                {
                    //alten Pfeil löschen
                    this.loeschePfeil();
                    //Stift bewegen
                    meinStift.bewegeUm(0.7);                                                      
                    //wenn l,L oder r,R wärend des Fluges gedrueckt
                    if (dieTastatur.wurdeGedrueckt()){
                        switch (dieTastatur.zeichen())
                        {
                            case 'l': case 'L':                      
                            //Pfeil drehen
                            meinStift.hoch();
                            meinStift.dreheUm(5);               
                            meinStift.runter();   
                            break;

                            case 'r': case 'R':
                            //Pfeil drehen
                            meinStift.hoch();
                            meinStift.dreheUm(-5);               
                            meinStift.runter();
                            break;
                        }
                        //Switch verlassen
                        dieTastatur.weiter();
                    }
                    //Abfrage ob der Pfeil außerhalb des Bildschirmes
                    
                    //Pfeil zeichnen
                    this.zeichnePfeil();
                    
                    //
                    if (meinStift.vPosition() < 0 || meinStift.vPosition() > 720 || meinStift.hPosition() <= 0)
                    {
                        //Pfeil loeschen
                        this.loeschePfeil();
                        //Stift zum beenden des Wurfes außerhalb der Dartscheibe platzieren                   
                        meinStift.bewegeBis(1000, 0);
                    }                   
                } while(meinStift.hPosition() < 1000);
                //Abfrage: Pfeil getroffen oder daneben
                if (meinStift.vPosition() > 140 && meinStift.vPosition() < 340)
                {
                    //und schreibe Nachricht:
                    meinStift.hoch();
                    meinStift.bewegeBis(950, 390);
                    meinStift.setzeFarbe(Farbe.GRUEN);
                    meinStift.runter();
                    meinStift.schreibeText("Treffer!");
                }
                else
                {
                    //Wenn Pfeil nicht auf der Dartscheibe dann
                    this.loeschePfeil();

                    //Und schreibe Nachricht:
                    meinStift.hoch();
                    meinStift.bewegeBis(950, 390);
                    meinStift.setzeFarbe(Farbe.ROT);
                    meinStift.runter();
                    meinStift.schreibeText("Daneben");                    
                }
                //Tastatur lösen, um "do/while"-Klammer zu verlassen
                if (dieTastatur.wurdeGedrueckt()){
                    dieTastatur.weiter();
                }
            }

        } while(!dieMaus.doppelKlick());
        // Aufraeumen
        meinStift.gibFrei();
        derBildschirm.gibFrei();
        dieMaus.gibFrei();
        dieTastatur.gibFrei();
    }
}
//Ende

