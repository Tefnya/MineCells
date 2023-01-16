from template_pools.template_pool import TemplatePoolGenerator


def generate_template_pools(poolgen: TemplatePoolGenerator):
    poolgen.generate_autoprefixed("minecells:promenade/chain_pile", [("0", 1), ("1", 2), ("2", 2), ("3", 2)], projection="terrain_matching")
    poolgen.generate_autoprefixed("minecells:promenade/gallows", [("0", 1), ("1", 2), ("2", 2), ("3", 2)])
    poolgen.generate_single("minecells:promenade/king_statue", projection="terrain_matching")
    poolgen.generate_autoprefixed("minecells:promenade/overground_buildings", [("0", 1)])
    poolgen.generate_autoprefixed("minecells:promenade/doorway", [("0", 2), ("1", 1), ("2", 1), ("3", 1), ("4", 1)])
    poolgen.generate_autoprefixed("minecells:promenade/doorway_decoration", [("banners", 1), ("torches", 2), ("minecraft:empty", 4)])
    poolgen.generate_autoprefixed("minecells:promenade/exterior_wall", [("0", 2), ("1", 2), ("2", 2), ("3", 2), ("4", 2), ("5", 2), ("6", 1), ("7", 1)])
