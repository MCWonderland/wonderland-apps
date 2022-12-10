package org.mcwonderland.web.resource

import org.mcwonderland.domain.features.TeamService
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("/teams")
class TeamResource(private val teamService: TeamService) {

    @GET
    fun listTeams(): Response {
        return Response.ok(mapOf("teams" to teamService.listTeams())).build()
    }


}