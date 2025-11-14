package com.recipe.user.service.datafetcher;


import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.recipe.user.codegen.types.Show;
import com.recipe.user.codegen.types.ShowCategory;
import com.recipe.user.codegen.types.ShowType;

import java.util.Collections;
import java.util.List;

@DgsComponent
public class LolomoDataFetcher {

    @DgsQuery
    public List<ShowCategory> lolomo(){


        return List.of(ShowCategory.newBuilder()
                        .name("Top 10")
                        .shows(
                                List.of(
                                        Show.newBuilder()
                                                .type(ShowType.MOVIE)
                                                .title("Stranger Things")
                                                .build()
                                )

                        )
                .build());
    }

}
