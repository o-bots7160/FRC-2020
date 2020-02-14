package frc.robot;

enum ColorMap{
     Rainbow(-0.97),Red(0.61),SkyBlue(0.83),White(0.93),Green(0.69),Yellow(0.69),ColorGradient(0.41);

     private ColorMap(final double colorCodes){
        this.colorCodes = colorCodes;
     }

     private double colorCodes;

     public double getColor(){
         return colorCodes;
     }



}








