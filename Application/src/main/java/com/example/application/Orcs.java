package com.example.application;


public abstract class Orcs extends Obstacle {
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

    @Override
    public boolean isAlive(){
        boolean flag = !(this.getLocation().getY() > 650);
        if(this.health <= 0) flag = false;
        if(!flag){
            this.iv.setDisable(true);
            this.iv.setOpacity(0);
        }
        return flag;
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

//        System.out.println("Locations :" + heroLoc.getY() + " " + orcLoc.getY());

        if(heroLoc.getY() - orcLoc.getY() >= 48){
            hero.damage();
//            System.out.println("Hero dies by orc");
            // hero dies
        } else if(orcLoc.getY() - heroLoc.getY() >= 80){
            //hero jumps and orc falls
            hero.setYspeed(-5);
            this.setYspeed(5);
//            System.out.println("Jump over orc");
        } else {
            //head on collision
            this.setXspeed(15);
//            System.out.println("Head on");
        }
    }

    protected void damage(int val){this.health -= val;}

    @Override
    public void ifWeaponCollides(Weapon w){
        this.damage(w.damagePower());
    }

    @Override
    public void ifObstacleCollides(Obstacle obs){
        if(obs instanceof TNT){
            obs.ifObstacleCollides(this);
            return;
        }
        if(obs instanceof BossOrc){
            this.setXspeed(-5);
            return;
        }
        //orc vs orc now
        Location Loc1 = this.getLocation();
        Location Loc2 = obs.getLocation();

//        System.out.println("Locations :" + Loc1.getY() + " " + Loc2.getY());

        if(Loc2.getY() - Loc1.getY() >= 40){
            this.setYspeed(-5);
            obs.setYspeed(5);
//            System.out.println("Ek ke upr ek");
            return ;
        }

        if(Loc1.getY() - Loc2.getY() >= 40){
            obs.setYspeed(-5);
            this.setYspeed(5);
//            System.out.println("Ek ke upr ek");
        }

        else{
            obs.setXspeed(10);
            this.setXspeed(0);
//            System.out.println("Side se dhakka");
        }

    }
}
