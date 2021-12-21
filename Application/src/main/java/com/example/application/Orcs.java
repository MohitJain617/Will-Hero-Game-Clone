package com.example.application;

public abstract class Orcs extends Obstacle{
    private int health;
    private Coins coins;

    public Orcs(double x, double y, int points, int health) {
        super(x, y);
        coins = new Coins(points);
        this.health = health;
    }
    public int getHealth(){
        return this.health;
    }
    @Override
    public Coins deathReward(){
        return this.coins;
    }

    //TODO: implement ifWeaponCollides(w: Weapon) here later
    protected void damage(int val){
        this.health -= val;
    }

    @Override
    public boolean isAlive(){
        if(this.getLocation().getY() > 720) return false;
        return (this.health > 0);
    }

    @Override
    public void ifHeroCollides(Hero hero){
        //3 cases
        Location heroLoc = hero.getLocation();
        Location orcLoc = this.getLocation();
//        System.out.println(orcLoc.getX()-heroLoc.getX());
//        System.out.println("Y axis: "+ (orcLoc.getY()-heroLoc.getY()));
        //near 65 is the sweet spot for head on collision
        //-51 for (orc - hero) for hero to die
        //80  for (orc - hero) for hero to jump
        if(orcLoc.getY()-heroLoc.getY() <= -50){
            hero.damage();
            System.out.println("Hero dies by orc");
            // hero dies
        } else if(orcLoc.getY() - heroLoc.getY() >= 80){
            //hero jumps and orc falls
            hero.setYspeed(-5);
            this.setYspeed(5);
            System.out.println("Jump over orc");
        } else {
            //head on collision
            this.setXspeed(15);
            System.out.println("Head on");
        }
    }
}
