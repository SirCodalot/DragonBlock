package me.codalot.dragonblock.game.players.skin;

import lombok.Getter;

@Getter
public enum SkinPreset {

    // TODO enter real values

    SUPER_SAIYAN_FOUR(
            "eyJ0aW1lc3RhbXAiOjE1NTAwNzY5Mzc3NDQsInByb2ZpbGVJZCI6ImRhNzQ2NWVkMjljYjRkZTA5MzRkOTIwMTc0NDkxMzU1IiwicHJvZmlsZU5hbWUiOiJLaWRTbGljZXIiLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzljNGQ5YzczYTFkOGIyMjE3NzI0YWJhYWFmNzNmNTYzZWZiMDJjNWMxOTU3ZGEwMjQ3NDMzOTEyMjYzZmZkYzMifX19",
            "ZbYJ0KBI2liUYGLgcQRlgE2ggapcXEwFTvl19xEhP7DEwLZm7CT+PkBqmjfNMAAfLHqgYdcKGhuFXhbdI9GmEWkjewl6/PyccRE2AVFYxAmPRazXWgO0qIszuVENf6AyBZyJXF8/0o4htVYb4lfGTj3xA1IToMERD5ULZ7keb+KJI9KToBmRl2qxI2N+EQZ8/VhkY1c/gEDuW7hsjGSaNdOmRkpJFXJY6OUuFaE28OzA7G9K8vXBiW2jeGd84HvIRdKRydjB0wVxrZuChbNVMcZ+hAIrnH2YZ5gyY8XZ5ATsT6SA07w6BNyOTJK2H4AD6tlA9dA+7CBBmNeByqogQyifEy7Y+BxLvwv1guhOyfg/03jV8gT3DRZh+3LN8xpks/gCZxngNOnyqmt6O5s2k6b4Q6DE/P32ZF2T1O+vPVeSDAsyj9HrHytdOw3xDRWX1BiAktq9gnud6m39Bng82cILirmJwVPeIm+aHQW9I+XYrveYLs3ocoEjPM1UsiznZy+0mfvRT6f3ibj4bhre1xAQH60ojI/pFkMEstEjxm/eiZNH5MO0hIF+j1hCbFPggFl8YeArCPG4kjW8NigHAnqqhZ0yiHcQWO0j0k2gmDIAJuToU46LuvHhI/8Awg88H27atB7fZWS+TWY6OoTnr+a+PcOCrKfIhQzLPQ8obsI="
    );

    private Skin skin;

    SkinPreset(String texture, String signature) {
        skin = new Skin(texture, signature);
    }

}
