/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StBarnabeTestModule } from '../../../test.module';
import { FideleDetailComponent } from 'app/entities/fidele/fidele-detail.component';
import { Fidele } from 'app/shared/model/fidele.model';

describe('Component Tests', () => {
    describe('Fidele Management Detail Component', () => {
        let comp: FideleDetailComponent;
        let fixture: ComponentFixture<FideleDetailComponent>;
        const route = ({ data: of({ fidele: new Fidele(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [StBarnabeTestModule],
                declarations: [FideleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FideleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FideleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fidele).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
