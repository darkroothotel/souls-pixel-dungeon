/*
 *
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
 *
 * Souls Pixel Dungeon
 * Copyright (C) 2024 Bartolomeo Cold
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.soulspixel.soulspixeldungeon.windows;

import com.soulspixel.soulspixeldungeon.Dungeon;
import com.soulspixel.soulspixeldungeon.items.Darksign;
import com.soulspixel.soulspixeldungeon.messages.Messages;
import com.soulspixel.soulspixeldungeon.scenes.GameScene;
import com.soulspixel.soulspixeldungeon.scenes.PixelScene;
import com.soulspixel.soulspixeldungeon.sprites.ItemSprite;
import com.soulspixel.soulspixeldungeon.ui.RedButton;
import com.soulspixel.soulspixeldungeon.ui.RenderedTextBlock;
import com.soulspixel.soulspixeldungeon.ui.Window;
import com.watabou.utils.Callback;

public class WndReturnDarksign extends Window {

    private static final float GAP	= 2;

    private static final int WIDTH_MIN = 120;
    private static final int WIDTH_MAX = 220;

    //only one WndInfoBonfire can appear at a time
    private static WndReturnDarksign INSTANCE;

    public WndReturnDarksign() {

        super();

        if (INSTANCE != null){
            INSTANCE.hide();
        }
        INSTANCE = this;

        fillFields();
    }

    @Override
    public void hide() {
        super.hide();
        if (INSTANCE == this){
            INSTANCE = null;
        }
    }

    private void fillFields() {

        IconTitle titlebar = new IconTitle(new ItemSprite().view(new Darksign()), Messages.get(this, "title"));
        titlebar.color( TITLE_COLOR );

        RenderedTextBlock txtInfo = PixelScene.renderTextBlock(Messages.get(this, "info"), 6 );

        layoutFields(titlebar, txtInfo);
    }

    private void layoutFields(IconTitle title, RenderedTextBlock info){
        int width = WIDTH_MIN;

        info.maxWidth(width);

        //window can go out of the screen on landscape, so widen it as appropriate
        while (PixelScene.landscape()
                && info.height() > 100
                && width < WIDTH_MAX){
            width += 20;
            info.maxWidth(width);
        }

        title.setRect( 0, 0, width, 0 );
        add( title );

        info.setPos(title.left(), title.bottom() + GAP);
        add( info );

        RedButton kill = new RedButton(Messages.get(this, "return")){
            @Override
            protected void onClick() {
                INSTANCE.hide();
                Dungeon.hero.sprite.operate(Dungeon.hero.pos, new Callback() {
                    @Override
                    public void call() {
                        Dungeon.hero.die(Dungeon.hero);
                    }
                });
            }
        };
        kill.setRect(0, info.bottom() + GAP, width, 20);
        add(kill);

        resize( width, (int)(kill.bottom() + 2) );
    }
}
