/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { VersementService } from 'app/entities/versement/versement.service';
import { IVersement, Versement } from 'app/shared/model/versement.model';

describe('Service Tests', () => {
    describe('Versement Service', () => {
        let injector: TestBed;
        let service: VersementService;
        let httpMock: HttpTestingController;
        let elemDefault: IVersement;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(VersementService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Versement(0, currentDate, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateVersement: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Versement', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateVersement: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateVersement: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Versement(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Versement', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateVersement: currentDate.format(DATE_FORMAT),
                        montantVersement: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateVersement: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Versement', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateVersement: currentDate.format(DATE_FORMAT),
                        montantVersement: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateVersement: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Versement', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
