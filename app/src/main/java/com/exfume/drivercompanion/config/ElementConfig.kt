package com.exfume.drivercompanion.config

import com.exfume.drivercompanion.R
import com.exfume.drivercompanion.util.Dsl.elements

object ElementConfig {
    val elements = elements {
        element {
            name = "fire"
            icon = R.drawable.ic_fire
            color = "#D50000"
            seal = R.string.selfDestruct
        }
        element {
            name = "water"
            icon = R.drawable.ic_water
            color = "#3F51B5"
            seal = R.string.stench
        }
        element {
            name = "earth"
            icon = R.drawable.ic_earth
            color = "#D1731F"
            seal = R.string.shackleDriver
        }
        element {
            name = "electric"
            icon = R.drawable.ic_electric
            color = "#FFEB3B"
            seal = R.string.backAttack
        }
        element {
            name = "wind"
            icon = R.drawable.ic_wind
            color = "#319C8F"
            seal = R.string.blowdown
        }
        element {
            name = "ice"
            icon = R.drawable.ic_ice
            color = "#4FC3F7"
            seal = R.string.shackleBlade
        }
        element {
            name = "light"
            icon = R.drawable.ic_light
            color = "#7F7527"
            seal = R.string.affinityDown
        }
        element {
            name = "dark"
            icon = R.drawable.ic_dark
            color = "#E040FB"
            seal = R.string.reinforcements
        }
    }

}