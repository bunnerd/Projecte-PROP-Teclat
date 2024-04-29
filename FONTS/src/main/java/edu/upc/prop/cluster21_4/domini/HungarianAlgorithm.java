package edu.upc.prop.cluster21_4.domini;

/**
 * Classe per calcular valor assignació òptima matriu de costos
 * @author Sergi Gonzalez Martos
 */

public class HungarianAlgorithm {

    private double max_valor;

   	private boolean Assig(double C_aux[][], int tecla, boolean posOcup[], int assigs[], int size) {
		for (int pos = 0; pos < size; ++pos) {

            //Per cada posició veiem dues coses:
            /*
             * 1) considerem que una tecla "tecla" es por assignar en una posició "pos" si C_aux[tecla][pos] = 0
             * 2) la posició a mirar està ocuapda?
             */
			
            if (C_aux[tecla][pos] == 0 && !posOcup[pos]) {
				
                //Marquem la posició
                posOcup[pos] = true; 

                //Si alguna de les següents afirmacions es compleix assignarem la tecla "tecla" a la posició "pos"
                /*
                 * 1) La posició "pos" no té cap tecla assignada
                 * 2) La tecla assignada a la posició "pos" té una posició alternativa on ser assignada
                 */
				if (assigs[pos] < 0 || Assig(C_aux, assigs[pos], posOcup, assigs, size)) {
					
                    //Assignem la tecla "tecla" a la posició "pos"
                    assigs[pos] = tecla;

					return true;
				}

			}
		}

		return false;
	}

    //Funció utilitzada per trobar la assginació màxima
	private int[] maxAssig(double C_aux[][], int size) {
		
        //assigs[i] -> i = posició | valor = tecla
        int assigs[] = new int[size];

        //Al principi no hi ha assignació
		for(int i = 0; i < size; ++i) assigs[i] = -1;
		
        //Per cada tecla mirem si té alguna assgnació
		for (int tecla = 0; tecla < size; ++tecla) {

            //posOcup[] ens serveix per saber quines posicions ja estan assignades (inicialitzem a false)
			boolean posOcup[] = new boolean[size];

            //Veiem si la tecla "tecla" es por assignar a alguna posició
			Assig(C_aux, tecla, posOcup, assigs, size);
		}

        //Tornem el array amb la assginació màxima
		return assigs;
	}

    //Funció utilitzada per restar valor mínim de cada fila i columna
    private void PreProces(double[][] C_aux, int size) {
        
        double val_min;
        //Restar valor minim de cada fila
        for (int fila = 0; fila < size; ++fila) {

            //Igualem val_min a +infinit per asegurar trobar un mínim
            val_min = Double.POSITIVE_INFINITY;
            
            //Per cada fila recorrem les seves columnes per trobar el val_min
            for (int columna = 0; columna < size; ++columna) {
                double val = C_aux[fila][columna];
                if (val < val_min) val_min = val;
            }

            //Una vegada trobat el valor_min el restem a totes les columnes de la fila
            for (int columna = 0; columna < size; ++columna) C_aux[fila][columna] -= val_min; 
        }

        //Restar valor minim de cada columna
        for (int columna = 0; columna < size; ++columna) {
            
            //Igualem val_min a +infinit per aseguar trobar un mínim
            val_min = Double.POSITIVE_INFINITY;
            
            //Per cada columna recorrem les seves files per trobar el val_min
            for (int fila = 0; fila < size; ++fila) {
                double val = C_aux[fila][columna];
                if (val < val_min) val_min = val;
            }

            //Una vegada trobat el valor_min el restem a totes les files de la columna
            for (int fila = 0; fila < size; ++fila) C_aux[fila][columna] -= val_min; 
        } 
    }

    /**
     * Funció publica que calcula l'assignació òptima d'una matriu de costos
     * @param C El paràmatre és la matriu de costos
     * @param size El paràmatre és el nombre de files de la matriu
     * @return Retorna el valor de l'assignació òptima de la matriu de costos
     */
    public double HungarianAlgorithmOPT(double[][] C, int size) {

        //L'array filas ens servirà per marcar les files durant el càlcul de línies mínimas
        boolean[] filas = new boolean[size];
        
        //L'array columnas ens servirà per marcar les columnes durant el càlcul de línies mínimas
        boolean[] columnas = new boolean[size];
        
        //Degut a que volem guardar els costos inicials de C creem una matriu auxiliar sobre la que treballar
        double[][] C_aux = new double[size][size];

        //Vector resultant assignació
        int[] res = new int[size];


        //Copiem els valors de C a C_aux
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) C_aux[i][j] = C[i][j];
        }

        //Fase iterativa de l'algorisme
        /*
         * 1) Restem a cada fila i columna el seu valor mínim
         * 2) Calculem el nombre mínim de línies necesaries per cobrir tots el zeros
         *    2.1) Calculem l'assignació màxima de la matriu C_aux i marquem les files que nos pertanyen a la assignació
         *    2.2) Marquem les columnes que tinguin algún zero en alguna fila marcada
         *    2.3) Marquem les files que tinguin la seva assignació a una columna marcada 
         *         (realitzem els passos 2.2 i 2.3 en bucle fins que el número de columnes marcades no canviï)
         *    2.4) El nombre mínim de línies = files no marcades + columnes marcades.
         *         Si el nombre de linies és = size mirar pas 3. 
         *         En cas contrari:
         *         2.4.1) Trobem el valor minim entre els números que no pertanyen a una línia
         *         2.4.2) Sumem el valor mínim a tots els valors que pertanyen a una línia 
         *                (si un valor pertany a dues línies sumarem el valor mínim dos cops)
         *         2.4.3) Restem el valor mínim de la matriu a tots els valors. Tornem al pas 2.1.
         * 3) Trobem l'assgnació òptima i retornem el seu valor
         */
        
        int num_lineas = 0;

        //Resta valor minim de cada fila i columna
        //Pas 1)
        PreProces(C_aux, size);

        res = maxAssig(C_aux, size);
        for (int i = 0; i < size; ++i) if (res[i] != -1) ++num_lineas;

        //Entrem en bucle mentre el número de línias mínim sigui diferent a size
        //Pas 2)
        while (num_lineas != size) {

            num_lineas = 0;

            //Reset arrays
            for (int i = 0; i < size; ++i) {
                
                //Inicialitzem a true totes les files i marcarem com a false 
                //aquelles que pertanyin a la assignació màxima
                filas[i] = true;

                //Inicialitzem a false totes les columnes i marcarem com a true
                //aquelles que tinquin algún zero en una fila marcada
                columnas[i] = false;
            }

            //Marquem files
            //Pas 2.1)

            //Trobem l'assignació màxima
            //res[i] -> i = ubicació | valor = tecla
            int[] res2 = new int[size];
            
            //aux2[i] -> i = tecla | valor = ubicació
            int[] aux2 = new int[size];
            res2 = maxAssig(C_aux, size);
            for (int j = 0; j < size; ++j) aux2[j] = -1;
            for (int i = 0; i < size; ++i) {
                if (res2[i] != -1) {
                    aux2[res2[i]] = i;
                    //Com res[i] forma part de la assgnació màxima marquem com a false la seva fila
                    filas[res2[i]] = false;
                    ++num_lineas;
                }
            }

            //col_ant i col_desp ens serveixen per veure si el número de columnes varia
            int col_ant = 1;
            int col_desp = 0;

            //Bucle on marquem columnes i files (segon cop)
            while (col_ant != col_desp) {
                
                col_ant = col_desp;
                
                //Marquem columnes
                //Pas 2.2)

                for (int i = 0; i < size; ++i) {
                    //Per cada fila que no pertanyi a la assignació mirem les seves columnes
                    if (filas[i] == true) {
                        for (int j = 0; j < size; ++j) {
                            //Si C_aux[i][j] és 0 i la columna no estaba marcada
                            if (C_aux[i][j] == 0.0 && columnas[j] == false) {
                                columnas[j] = true;
                                ++num_lineas;
                                //+1 el número de columnes marcades
                                ++col_desp;
                            }
                        }
                    }
                }
    
                //Marquem files segona part
                //Pas 2.3)

				for (int i = 0; i < size; ++i) {
                    //Per cada fila que pertanyi a la assignació mirem si la seva assignació
                    //està en una columna marcada
					if (filas[i] == false) {
						if (columnas[aux2[i]] == true) {
                            filas[i] = true;
                            --num_lineas;
                        }
					}
				}
            }

            //Comprovem num lineas necesaries
            //Pas 2.4)

            //Inicialitzem el valor min a +infint per asegurar trobar un mínim
            double min = Double.POSITIVE_INFINITY;
            
            //Solament realitzem els següents passos si num_lineas != size
            if (num_lineas != size) {
                
                //Calcular valor minim no cobert
                //Pas 2.4.1)

                //Busquem tots els valors que no pertanyin a nignuna línia
                for (int i = 0; i < size; ++i) {
                    //Si la fila està marcada seguim
                    if (filas[i] == true) {
                        for (int j = 0; j < size; ++j) {
                            //Si la columna no està marcada seguim
                            if (columnas[j] == false) {
                                //Guardem el mínim entre min u C_aux[i][j]
                                if (C_aux[i][j] < min) min = C_aux[i][j];
                            }
                        }
                    }
                }
                
                //Pas 2.4.2 i 2.4.3)

                //Restem el mínim valor a tots els valors de la matriu C_aux
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        if (filas[i] == false) C_aux[i][j] = C_aux[i][j] + min;
                        if (columnas[j] == true) C_aux[i][j] = C_aux[i][j] + min;
                    }
                }
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        C_aux[i][j] = C_aux[i][j] - min;
                    }
                }
            }
        }

        //Pas 3)

        //Busquem la assginació òptima (l'algorisme ens asegura que la asignació màxuma també és la òptima)
		double max = 0;

        //aux3[i] -> i = posició | valor = tecla;
        int[] res3 = new int[size];
		res3 = maxAssig(C_aux, size);

        //Utilitzem la matriu original C per sumar els costos de la assignació òptima
		for (int i = 0; i < size; ++i) max += C[res3[i]][i];

        return max;
    }
}
