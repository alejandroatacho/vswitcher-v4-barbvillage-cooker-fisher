//Detect Fishing Level so we can decide what to do when.
int lvl = client.getBoostedSkillLevel(Skill.FISHING);
// Fish IDs
int trout = 335;
int salmon = 331;
int counter_trout = v.getInventory().count(trout);
int counter_salmon = v.getInventory().count(salmon);
//Cook ID
int cooked_salmon = 329;
int cooked_trout = 333;
int burned_fish = 343;
int counter_cooked_trout = v.getInventory().count(cooked_trout);
int counter_cooked_salmon = v.getInventory().count(cooked_salmon);
int counter_burned_fish = v.getInventory().count(burned_fish);
int fire_pit = 43475;
// Get nearest fishing spot
NPC fishingSpot = v.getNpc().findNearest(1526);
void fish() {
    handleRunning();
    if (v.getLocalPlayer().hasAnimation(-1) && !v.getInventory().inventoryFull() && v.getWalking().isIdle()) {
        v.getFishing().lure(fishingSpot);
    } else if (counter_trout + counter_salmon >= 26 && (lvl < 30 || lvl >= 30)) {
        cookOurFish();
    } 
    else if (counter_cooked_trout + counter_salmon + counter_burned_fish >= 26 && (lvl < 30 || lvl >= 30) && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving())
    {
        cookOurFish();
    }
    else if (counter_cooked_salmon + counter_trout + counter_burned_fish >= 26 && (lvl < 30 || lvl >= 30) && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving())
    {
        cookOurFish();
    }
    else {
        manageInventory();
    }
}
void cookOurFish() {
    GameObject firePit = v.getGameObject().findNearest(fire_pit);
    if (firePit != null && !v.getCooking().isCooking()) {
        int firePitSceneX = firePit.getSceneMinLocation().getX();
        int firePitSceneY = firePit.getSceneMinLocation().getY();

        if (v.getLocalPlayer().hasAnimation(-1) && counter_trout >= 26 && lvl < 30 && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving()) {
            v.invoke("Cook","<col=ffff>Fire",fire_pit,3,firePitSceneX,firePitSceneY,false);
            v.getCallbacks().afterTicks(4, () -> {
                v.invoke("Cook","<col=ff9040>Raw trout</col>",1,57,-1,17694734,false);
            });
        }
        else if (lvl >= 30) {
            if (counter_trout + counter_salmon >= 26 && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving() && !v.getCooking().isCooking()) {
                v.getCallbacks().afterTicks(8, () -> {
                v.invoke("Cook","<col=ffff>Fire",fire_pit,3,firePitSceneX,firePitSceneY,false);
                v.getCallbacks().afterTicks(4, () -> {
                    v.invoke("Cook","<col=ff9040>Raw trout</col>",1,57,-1,17694734,false);
                }); });
            }
            else if (counter_cooked_salmon + counter_trout + counter_burned_fish >= 26 && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving() && counter_salmon == 0 && !v.getCooking().isCooking()) {
                v.invoke("Cook","<col=ffff>Fire",fire_pit,3,firePitSceneX,firePitSceneY,false);
                v.getCallbacks().afterTicks(4, () -> {
                    v.invoke("Cook","<col=ff9040>Raw trout</col>",1,57,-1,17694734,false);
                });
            }
                 else if (counter_cooked_trout + counter_salmon + counter_burned_fish >= 26 && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving() && counter_trout == 0 && !v.getCooking().isCooking()) {
                v.invoke("Cook","<col=ffff>Fire",fire_pit,3,firePitSceneX,firePitSceneY,false);
                v.getCallbacks().afterTicks(4, () -> {
                    v.invoke("Cook","<col=ff9040>Raw salmon</col>",1,57,-1,17694734,false);
                });
            }
            // else if (counter_cooked_trout + counter_cooked_salmon + counter_burned_fish != 26 && v.getLocalPlayer().hasAnimation(-1) && (counter_trout + counter_salmon != 0))
            // {
            //     v.invoke("Cook","<col=ffff>Fire",fire_pit,3,firePitSceneX,firePitSceneY,false);
            //     v.getCallbacks().afterTicks(4, () -> {
            //         if (v.getInventory().hasIn(salmon)){
            //         v.invoke("Cook","<col=ff9040>Raw salmon</col>",1,57,-1,17694734,false);
            //         }
            //     });
            //     else if (v.getInventory().hasIn(trout)){
            //       v.invoke("Cook","<col=ff9040>Raw trout</col>",1,57,-1,17694734,false);
            //     }
            // }
        }
    }
} 
void handleRunning() {
    if (client.getEnergy() >= 2000) {
        v.getWalking().turnRunningOn();
    }
}
void manageInventory() {
    if (counter_cooked_salmon + counter_cooked_trout + counter_burned_fish == 26 && lvl >= 30 && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving()) {
        v.getInventory().drop(cooked_trout, burned_fish, cooked_salmon);
    }
    else if (counter_cooked_trout + counter_burned_fish == 26 && lvl < 30 && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving())
    {
        v.getInventory().drop(cooked_trout, burned_fish);
    }
}
fish();