package md;

import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class TelaController {

	@FXML
	private TextField coef, exp;

	@FXML
	private TextArea root,equacao;

	@FXML
	private void calcular() {
		root.clear();
		try {
			DecimalFormat df = new DecimalFormat("#.00000000");
			int tam = Integer.parseInt(exp.getText());
			if(tam == 0) {
				if(coef.getText().equals("0"))
					root.setText("Todos os pontos são raízes.");
				else root.setText("Não existem raízes nesse intervalo");
			}
			else {
				double[] num = new double[tam + 1];
				StringTokenizer token = new StringTokenizer(coef.getText(), ";");
				if((tam+1) < token.countTokens())
					JOptionPane.showMessageDialog(null, "Favor digitar os coeficientes corretamente");
				else {
					for (; tam>=0; tam--)
						num[tam] = Double.parseDouble(token.nextToken());
					double[] deriv = geraDeriv(num);
					String[] raizes = new String [Integer.parseInt(exp.getText())];
					for(int aux = 0; aux < raizes.length; aux ++)
						raizes[aux] = "";
					int cont = 0;
					double encontrada;
					for (double x = -10.0; x < 10.0; x += 0.1) {
						double img = calcImagem(num, x);
						double imgDeriv = calcImagem(deriv, x);
						double img1 = calcImagem(num, x + 0.1);
						double img1Deriv = calcImagem(deriv, x + 0.1);
						if (img * img1 == 0) {
							if (img == 0) {
								if(!verificaRaiz(raizes, df.format(x)))
									raizes[cont++] = ""+x;
							}
						} else if (img * img1 > 0 && imgDeriv * img1Deriv < 0) {
							if (imgDeriv < img1Deriv) {
								if(!verificaRaiz(raizes, df.format(encontrada = calcRaiz(num, deriv, x)))) {
									if(encontrada < 10 && encontrada > -10)
										raizes[cont++] = df.format(encontrada);
									else root.setText("Não foram encontradas raízes no intervalo [-10,10]");

								}
							}
							else {
								if(!verificaRaiz(raizes, df.format(encontrada = calcRaiz(num, deriv, x + 0.1)))) {
									if(encontrada < 10 && encontrada > -10)
										raizes[cont++] = df.format(encontrada);
									else root.setText("Não foram encontradas raízes no intervalo [-10,10]");
								}
							}
						} else if (img * img1 < 0) {
							if (imgDeriv < img1Deriv) {
								if(!verificaRaiz(raizes, df.format(encontrada = calcRaiz(num, deriv, x)))) {
									if(encontrada < 10 && encontrada > -10)
										raizes[cont++] = df.format(encontrada);
									else root.setText("Não foram encontradas raízes no intervalo [-10,10]");


								}
							}
							else {
								if(!verificaRaiz(raizes, df.format(encontrada = calcRaiz(num, deriv, x+0.1)))) {
									if(encontrada < 10 && encontrada > -10)
										raizes[cont++] = df.format(encontrada);
									else root.setText("Não foram encontradas raízes no intervalo [-10,10]");

								}
							}
						}
					}
					mostraEquacao(num);
					for(int aux = 0; aux<raizes.length ; aux++) {
						root.setText(root.getText() + "  " + raizes[aux]);
					}
				}
			}
		}catch (NumberFormatException erro) {
			JOptionPane.showMessageDialog(null, "Favor digitar um número no formato válido");
		}catch(NegativeArraySizeException erro) {
			JOptionPane.showMessageDialog(null, "Favor Digitar somente equações polinomiais de expoente positivo");
		}catch(NoSuchElementException erro) {
			JOptionPane.showMessageDialog(null, "Favor digitar os coeficientes corretamente");
		}catch(StackOverflowError erro) {
			root.setText("Não foram encontradas raízes no intervalo [-10,10]");
		}
	}

	private double[] geraDeriv(double[] num) {
		double[] deriv = new double[num.length - 1];
		for (int i = num.length-1; i > 0; i--)
			deriv[i - 1] = num[i] * i;
		return deriv;
	}

	private double calcImagem(double[] num, double x) {
		double result = 0;
		for (int i = 0; i < num.length; i++)
			result += Math.pow(x, i) * num[i];
		return result;
	}

	private double calcRaiz(double[] num, double[] deriv, double valor) {
		double raiz;
		double teste = calcImagem(deriv, valor);
		if (teste != 0) {
			raiz = valor - (calcImagem(num, valor) / teste);
			if(Math.abs(raiz-valor)>0.000001)
				return calcRaiz(num,deriv,raiz);
			else return raiz;
		} else
			return valor;
	}


	private boolean verificaRaiz(String[] raizes, String nova) {
		boolean achou = false;
		for(int i = 0; i<raizes.length; i++) {
			if(raizes[i].equals(nova))
				achou = true;
		}
		return achou;
	}
	
	private void mostraEquacao(double[] num) {
		equacao.setText("");
		equacao.setVisible(true);
		for(int aux = num.length-1; aux > 0; aux--) 
			equacao.setText(num[aux-1]>=0? equacao.getText() + num[aux] + "x^" + aux + " + " : equacao.getText() + num[aux] + "x^" + aux + " ");
		equacao.setText(equacao.getText() + num[0]);
	}

	@FXML 
	private void clique(MouseEvent e) {
		calcular();
	}

	@FXML 
	private void enter(KeyEvent e) {
		if(e.getCode()== KeyCode.ENTER){
			calcular();
		}
	}
}
