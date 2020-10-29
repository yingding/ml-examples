import {HttpHeaders} from '@angular/common/http';

export default class HttpUtils {
  static genJsonHttpHeaders(): HttpHeaders {
    return new HttpHeaders({'Content-Type': 'application/json', Authorization: '', 'Csrf-Token': 'nocheck'});
  }
  static genSeedHttpBody(): object {
    return { seed: 'seedToChange' };
  }
  static genHttpOptions(headers: HttpHeaders): object {
    return { headers };
  }
}
