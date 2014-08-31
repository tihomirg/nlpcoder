package dialogtest;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import search.ISText;
import search.config.SearchConfig;
/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "DialogTest"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private ISText iSText;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	
		SearchConfig.setDeclarationInputUnmatchingWeight(0.01);
		SearchConfig.setPrimaryWeight(0.6);
		SearchConfig.setSecondaryWeight(0.4);
		SearchConfig.setRelatedWeigthFactor(0.5);
		SearchConfig.setDeclarationScorerCoefs(new Double[]{0.8, 0.2});
		SearchConfig.setKindMatrix(new double[][]{{0.5, 0.2},{0.2, 0.5}});

		/*
		 * 
PartialExpressionBadMergePenalty : 1.2
PartialExpressionMergeSizePenalty : 0.5
PartialExpressionMergeReward : 0.09999999999999998
PartialExpressionIndividualSize : 3
PartialExpressionIndividualSizePenalty : 0.3

CompositionWeightFactor : 0.4
InputExprRepetitionPenalty : 0.9999999999999999
HoleWeight : 0.09999999999999998

InputExprWeight : 0.7
		 * 
		 * 	private static double localVariableWeight = 0.6;
	private static double numberLiteralWeight = 0.6;
	private static double stringLiteralWeight = 0.6;
	private static double booleanLiteralWeight = 0.6;
		 * 
		 * 
		 */
		
		SearchConfig.setPartialExpressionBadMergePenalty(1.2);
		SearchConfig.setPartialExpressionMergeSizePenalty(0.5);
		SearchConfig.setPartialExpressionMergeReward(0.1);
		SearchConfig.setPartialExpressionIndividualSize(3);
		SearchConfig.setPartialExpressionIndividualSizePenalty(0.3);

		SearchConfig.setHoleWeight(0.1);
		SearchConfig.setInputExprRepetitionPenalty(1.0);
		SearchConfig.setCompositionWeightFactor(0.4);
		
		double inputExprWeight = 0.7;
		SearchConfig.setLocalVariableWeight(inputExprWeight);
		SearchConfig.setNumberLiteralWeight(inputExprWeight);
		SearchConfig.setStringLiteralWeight(inputExprWeight);
		SearchConfig.setBooleanLiteralWeight(inputExprWeight);
		
		this.iSText = new ISText(10);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public ISText getISText() {
		return iSText;
	}
}
