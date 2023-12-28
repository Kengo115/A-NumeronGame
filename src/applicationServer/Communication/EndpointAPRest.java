package applicationServer.Communication;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Message.MakeRoomMessage;
import applicationServer.controller.ApplicationController;
import com.google.gson.Gson;

@Path("/")
public class EndpointAPRest {

    static Gson gson = new Gson(); //gsonメッセージ
    ApplicationController applicationController = new ApplicationController();


    @Path("/makeRoom")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postSample(String requestBody) {

		try{

			// リクエストメッセージの処理
			MakeRoomMessage makeRoomMessage = gson.fromJson(requestBody, MakeRoomMessage.class);

			//デバック
			System.out.println("Restアプリケーションサーバ到達");
			
			//ルーム作成
			applicationController.makeRoom(makeRoomMessage.username1,makeRoomMessage.username2);

			//デバック
			System.out.println("部屋作成");

			return Response.ok().entity(gson.toJson(makeRoomMessage)).build();

		} catch (Exception e) {

			e.printStackTrace();
			int status = 400;
			return Response.status(status).build();

		}
	}
}
