# Funkcija za izris labirintov:

library(grid)
setwd("C:/Users/HP/Desktop/Umetna/src")

plotLabyrinth <- function(lab)
{
  sel <- lab[,] == -1
  lab[sel] <- rgb(0, 0, 0)		
  
  sel <- lab[,] == 0
  lab[sel] <- rgb(1, 1, 1)
  
  sel <- lab[,] == -2
  lab[sel] <- rgb(1, 0, 0)
  
  sel <- lab[,] == -3
  lab[sel] <- rgb(1, 1, 0)
  
  sel <- lab[,] >= 1
  lab[sel] <- rgb(0.3, 0.3, 0.3)
  
  grid.newpage()
  grid.raster(lab, interpolate=F)
}

data <- read.table("labyrinth_3.txt", sep=",", header=F)
data <- as.matrix(data)
data
screen <- plotLabyrinth(data)

# Funkcija za izris rešitve labirinta:

plotResult <- function(lab) {
  
    sel <- lab[,] == -5
    lab[sel] <- rgb(0, 1, 0)
  
    sel <- lab[,] == -1
    lab[sel] <- rgb(0, 0, 0)

    sel <- lab[,] == 0
    lab[sel] <- rgb(1, 1, 1)

    sel <- lab[,] == -2
    lab[sel] <- rgb(1, 0, 0)

    sel <- lab[,] == -3
    lab[sel] <- rgb(1, 1, 0)

    sel <- lab[,] >= 1
    lab[sel] <- rgb(0.3, 0.3, 0.3)

    grid.newpage()
    grid.raster(lab, interpolate=F)
}

data <- read.table("rezultat.txt", sep=" ", header=F)
data <- as.matrix(data)
data
screen <- plotResult(data)
  