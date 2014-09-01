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
		
		SearchConfig.setPartialExpressionBadMergePenalty(1.275);
		SearchConfig.setPartialExpressionMergeSizePenalty(0.5);
		SearchConfig.setPartialExpressionMergeReward(0.05);
		SearchConfig.setPartialExpressionIndividualSize(3);
		SearchConfig.setPartialExpressionIndividualSizePenalty(0.55);

		SearchConfig.setHoleWeight(0.05);
		SearchConfig.setInputExprRepetitionPenalty(1.05);
		SearchConfig.setCompositionWeightFactor(0.45);
		
		double inputExprWeight = 1.35;
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
