package org.projekt.multimediaplayer.gui;

import java.awt.*;
/**
 * The Class GBC -  Klasa pomocnicza u¿ywana do rozmieszczania elementów za pomoc¹ GridBagLayout.
 */
public class GBC extends GridBagConstraints
{
   /**
    * Tworzy obiekt typu GBC z podanymi wartoœciami gridx i gridy oraz wszystkimi pozosta³ymi
    * parametrami ustawionymi na wartoœci domyœlne.
    * @param gridx wspó³rzêdna gridx
    * @param gridy wspó³rzêdna gridy
    */
   public GBC(int gridx, int gridy)
   {
      this.gridx = gridx;
      this.gridy = gridy;
   }

   /**
    * Tworzy obiekt typu GBC z podanymi wartoœciami gridx, gridy, gridwidth i gridheight oraz wszystkimi pozosta³ymi
    * parametrami ustawionymi na wartoœci domyœlne.
    * @param gridx wspó³rzêdna gridx
    * @param gridy wspó³rzêdna gridy
    * @param gridwidth liczba zajmowanych komórek w poziomie
    * @param gridheight liczba zajmowanych komórek w pionie
    */
   public GBC(int gridx, int gridy, int gridwidth, int gridheight)
   {
      this.gridx = gridx;
      this.gridy = gridy;
      this.gridwidth = gridwidth;
      this.gridheight = gridheight;
   }

   /**
    * Ustawia parametr anchor.
    * @param anchor wartoœæ parametru anchor
    * @return this obiekt do dalszej modyfikacji
    */
   public GBC setAnchor(int anchor)
   {
      this.anchor = anchor;
      return this;
   }

   /**
    * Ustawia kierunek zape³niania.
    * @param fill kierunek zape³niania
    * @return this obiekt do dalszej modyfikacji
    */
   public GBC setFill(int fill)
   {
      this.fill = fill;
      return this;
   }

   /**
    * Ustawia parametry weigh komórek.
    * @param weightx parametr weigh w poziomie
    * @param weighty parametr weigh w pionie
    * @return this obiekt do dalszej modyfikacji
    */
   public GBC setWeight(double weightx, double weighty)
   {
      this.weightx = weightx;
      this.weighty = weighty;
      return this;
   }

   /**
    * Ustawia dodatkow¹ pust¹ przestrzeñ w komórce.
    * @param distance dope³nienie we wszystkich kierunkach
    * @return this obiekt do dalszej modyfikacji
    */
   public GBC setInsets(int distance)
   {
      this.insets = new Insets(distance, distance, distance, distance);
      return this;
   }

   /**
    * Ustawia dope³nienia w komórce.
    * @param top odstêp od krawêdzi górnej
    * @param left odstêp od krawêdzi lewej
    * @param bottom odstêp od krawêdzi dolnej
    * @param right odstêp od krawêdzi prawej
    * @return obiekt do dalszej modyfikacji
    */
   public GBC setInsets(int top, int left, int bottom, int right)
   {
      this.insets = new Insets(top, left, bottom, right);
      return this;
   }

   /**
    * Ustawia dope³nienie wewnêtrzne.
    * @param ipadx dope³nienie wewnêtrzne poziome
    * @param ipady dope³nienie wewnêtrzne pionowe
    * @return obiekt do dalszej modyfikacji
    */
   public GBC setIpad(int ipadx, int ipady)
   {
      this.ipadx = ipadx;
      this.ipady = ipady;
      return this;
   }
}
