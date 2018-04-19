package com.exfume.drivercompanion.config

import com.exfume.drivercompanion.util.Dsl.elementCombos

object CombosConfig {
    val combos = elementCombos {
        combo {
            element = "fire"
            rightTree {
                element = "fire"
                rightTree {
                    element = "fire"
                }
                leftTree {
                    element = "light"
                }
            }
            leftTree {
                element = "water"
                rightTree {
                    element = "fire"
                }
                leftTree {
                    element = "ice"
                }
            }
        }
        combo {
            element = "earth"
            rightTree {
                element = "earth"
                rightTree {
                    element = "electric"
                }
            }
            leftTree {
                element = "fire"
                rightTree {
                    element = "earth"
                }
                leftTree {
                    element = "wind"
                }
            }
        }
        combo {
            element = "wind"
            rightTree {
                element = "wind"
                rightTree {
                    element = "earth"
                }
                leftTree {
                    element = "electric"
                }
            }
            leftTree {
                element = "ice"
                rightTree {
                    element = "ice"
                }
            }
        }
        combo {
            element = "light"
            rightTree {
                element = "light"
                rightTree {
                    element = "light"
                }
                leftTree {
                    element = "water"
                }
            }
            leftTree {
                element = "electric"
                rightTree {
                    element = "fire"
                }
            }
        }
        combo {
            element = "water"
            rightTree {
                element = "water"
                rightTree {
                    element = "water"
                }
                leftTree {
                    element = "dark"
                }
            }
            leftTree {
                element = "earth"
                rightTree {
                    element = "wind"
                }
            }
        }
        combo {
            element = "electric"
            rightTree {
                element = "electric"
                rightTree {
                    element = "water"
                }
            }
            leftTree {
                element = "fire"
                rightTree {
                    element = "wind"
                }
                leftTree {
                    element = "ice"
                }
            }
        }
        combo {
            element = "ice"
            rightTree {
                element = "ice"
                rightTree {
                    element = "earth"
                }
                leftTree {
                    element = "dark"
                }
            }
            leftTree {
                element = "water"
                rightTree {
                    element = "wind"
                }
            }
        }
        combo {
            element = "dark"
            rightTree {
                element = "dark"
                rightTree {
                    element = "dark"
                }
                leftTree {
                    element = "earth"
                }
            }
            leftTree {
                element = "light"
                rightTree {
                    element = "electric"
                }
            }
        }
    }
}