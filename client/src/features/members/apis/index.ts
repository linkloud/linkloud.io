import { request } from "@/lib/axios";

import { Member } from "../types";
import { SingleDataResponse } from "@/types";

const memberApi = {
  me(token: string): Promise<SingleDataResponse<Member>> {
    return request.get("/member/me", {
      headers: { Authorization: `Bearer ${token}` },
    });
  },
};

export default memberApi;
