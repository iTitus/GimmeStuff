package io.github.ititus.gimmestuff.client.model;

import java.util.List;
import javax.vecmath.Matrix4f;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.lang3.tuple.Pair;

import io.github.ititus.gimmestuff.block.BlockInfiniteFluid;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteFluid;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Credits go to the SlimeKnights team (TiCon, Mantle)
 */
@SideOnly(Side.CLIENT)
public class ModelInfiniteFluid implements IPerspectiveAwareModel {

	private final IPerspectiveAwareModel standardModel;
	private final LoadingCache<String, IBakedModel> modelCache;

	public ModelInfiniteFluid(IPerspectiveAwareModel standardModel, IRetexturableModel infiniteFluidModel, VertexFormat format) {
		this.standardModel = standardModel;

		Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
		ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = getTransforms(standardModel);

		this.modelCache = CacheBuilder.newBuilder().build(new CacheLoader<String, IBakedModel>() {
			public IBakedModel load(String texture) throws Exception {
				ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
				builder.put("fluid", texture);
				IModel retexturedModel = infiniteFluidModel.retexture(builder.build());
				IModelState modelState = new SimpleModelState(transforms);

				return retexturedModel.bake(modelState, format, textureGetter);
			}
		});
	}

	public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms(IPerspectiveAwareModel model) {
		ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = ImmutableMap.builder();
		for (ItemCameraTransforms.TransformType type : ItemCameraTransforms.TransformType.values()) {
			TRSRTransformation transformation = new TRSRTransformation(model.handlePerspective(type).getRight());
			if (!transformation.equals(TRSRTransformation.identity())) {
				builder.put(type, TRSRTransformation.blockCenterToCorner(transformation));
			}
		}
		return builder.build();
	}

	protected IBakedModel getActualModel(String texture) {
		return Strings.isNullOrEmpty(texture) ? standardModel : modelCache.getUnchecked(texture);
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		String texture = null;
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState extendedState = (IExtendedBlockState) state;
			FluidStack fluidStack = extendedState.getValue(BlockInfiniteFluid.FLUID);
			if (fluidStack != null) {
				texture = fluidStack.getFluid().getStill(fluidStack).toString();
			}
		}
		return getActualModel(texture).getQuads(state, side, rand);
	}

	@Override
	public boolean isAmbientOcclusion() {
		return standardModel.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return standardModel.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return standardModel.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return standardModel.getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return standardModel.getItemCameraTransforms();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return InfiniteFluidOverrideList.INSTANCE;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		Pair<? extends IBakedModel, Matrix4f> pair = standardModel.handlePerspective(cameraTransformType);
		return Pair.of(this, pair.getRight());
	}

	private static class InfiniteFluidOverrideList extends ItemOverrideList {

		public static InfiniteFluidOverrideList INSTANCE = new InfiniteFluidOverrideList();

		private InfiniteFluidOverrideList() {
			super(ImmutableList.of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
			if (originalModel instanceof ModelInfiniteFluid) {
				FluidStack fluidStack = ItemBlockInfiniteFluid.getFluidStack(stack);
				if (fluidStack != null) {
					return ((ModelInfiniteFluid) originalModel).getActualModel(fluidStack.getFluid().getStill(fluidStack).toString());
				}
			}
			return originalModel;
		}
	}
}
